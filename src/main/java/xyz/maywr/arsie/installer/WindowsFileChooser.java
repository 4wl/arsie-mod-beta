package xyz.maywr.arsie.installer;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.CommonDialogs;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;

public class WindowsFileChooser extends JFileChooser {

    private static boolean FX_AVAILABLE;

    private static Preferences preferences = Preferences.userRoot().node("java.cross.FileChooser");

    private List<File> selectedFiles;
    private File selectedFile;
    private File currentDirectory;
    private final List<CommonDialogs.ExtensionFilter> filters = new ArrayList<>();

    static {
        try {
            System.setProperty("glass.disableThreadChecks", "true");
            Class.forName("com.sun.glass.ui.Application");
            FX_AVAILABLE = true;
        } catch (Exception e) {
            FX_AVAILABLE = false;
        }
    }

    public WindowsFileChooser() {
        super();
        if (isAcceptAllFileFilterUsed())//???????true??????fixAddFilter
            setAcceptAllFileFilterUsed(true);
    }

    public WindowsFileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
        if (isAcceptAllFileFilterUsed())
            setAcceptAllFileFilterUsed(true);
    }

    public WindowsFileChooser(File currentDirectory) {
        super(currentDirectory);
        if (isAcceptAllFileFilterUsed())
            setAcceptAllFileFilterUsed(true);
    }

    private static Map<String, Method> methods = new HashMap<>();

    private static Method getNativeJfxMethod(String name) throws Exception {

        if (Application.GetApplication() == null) {
            //Initializes native and event loop
            Application.run(() -> {
            });
        }

        Method f = methods.get(name);
        if (f == null) {
            Class app = Class.forName(isWindows() ?
                    "com.sun.glass.ui.win.WinCommonDialogs" :
                    "com.sun.glass.ui.gtk.GtkCommonDialogs");
            Method[] mds = app.getDeclaredMethods();
            for (Method md : mds) {
                if (md.getName().equals(name)) {
                    f = md;
                    break;
                }
            }
            if (f == null)
                throw new NoSuchMethodException(name);
            f.setAccessible(true);
            methods.put(name, f);
        }
        return f;
    }

    private static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().startsWith("wind");
    }

    private static long getWinID(Component w) {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("No native windows when headless");
        }
        if (w.isLightweight()) {
            throw new IllegalArgumentException("Component must be heavyweight");
        }
        if (!w.isDisplayable())
            throw new IllegalStateException("Component must be displayable");

        long winid = -1;
        try {

            Class<?> cl = Class.forName("java.awt.Component");
            java.lang.reflect.Field f = cl.getDeclaredField("peer");//around java9
            f.setAccessible(true);
            Object peer = f.get(w);

            if (isWindows()) {
                cl = Class.forName("sun.awt.windows.WComponentPeer");
                f = cl.getDeclaredField("hwnd");
                f.setAccessible(true);
                winid = f.getLong(peer);
            } else {
                cl = Class.forName("sun.awt.X11ComponentPeer");
                java.lang.reflect.Method m = cl.getMethod("getContentWindow", null);
                Object obj = m.invoke(peer);
                winid = Long.parseLong(obj.toString());
            }
        } catch (Throwable ex) {
        }
        return winid;
    }

    public void updateUI() {
        //?setui????use.NativeJFileChooser?????BasicFileChooserUI???????????
        //?????????
        putClientProperty("use.NativeJFileChooser", "true");
        super.updateUI();
    }

    @Override
    public int showOpenDialog(Component parent) {
        return show(parent, CommonDialogs.Type.OPEN);
    }

    private int show(Component parent, int type) {
        if (!FX_AVAILABLE) {
            if (type == CommonDialogs.Type.OPEN)
                return super.showOpenDialog(parent);
            else
                return super.showSaveDialog(parent);
        }


        boolean ontop = false;
        if (!isWindows() && parent instanceof Window) {
            ontop = ((Window) parent).isAlwaysOnTop();
            ((Window) parent).setAlwaysOnTop(false);//????
        }

        final CountDownLatch latch = new CountDownLatch(1);

        Runnable exec = (() -> {
            try {
                long winID = -1;
                if (parent != null)
                    try {
                        parent.setEnabled(false);
                        winID = getWinID(parent);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                winID = (winID == -1 ? 0L : winID);

                if (!isWindows())
                    winID = 0L;//todo linux ??winID???????,?crash,????null??

                List<File> xFiles;

                String dir = "";
                try {
                    dir = getCurrentDirectory() == null ? "" : getCurrentDirectory().getCanonicalPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (getFileSelectionMode() == DIRECTORIES_ONLY) {
                    Method f = getNativeJfxMethod("_showFolderChooser");
                    String x = (String) f.invoke(f.getDeclaringClass(),//1.7????????
                            winID,
                            dir,
                            getDialogTitle() == null ? "" : getDialogTitle()
                    );
                    xFiles = new ArrayList<>();
                    if (x != null)
                        xFiles.add(new File(x));
                } else {
                    Method f = getNativeJfxMethod("_showFileChooser");
                    CommonDialogs.FileChooserResult x = (CommonDialogs.FileChooserResult) f.invoke(f.getDeclaringClass(),//1.7????????
                            winID,
                            dir,
                            getSelectedFile() == null ? "" : getSelectedFile().getName(),
                            getDialogTitle() == null ? "" : getDialogTitle(),
                            type,
                            (type != CommonDialogs.Type.SAVE) && isMultiSelectionEnabled(),
                            filters.toArray(new CommonDialogs.ExtensionFilter[]{}),
                            filters.size() > 1 ? 1 : -1);//1 todo ????setFileFilter?????

                    xFiles = x.getFiles();
                }

                selectedFiles = xFiles;
                selectedFile = (xFiles != null && xFiles.size() > 0) ? xFiles.get(0) : null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (parent != null)
                    parent.setEnabled(true);
                latch.countDown();
            }
        });

        new Thread(exec).start();

        try {
            latch.await();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        if (!com.sun.jna.Platform.isWindows() && parent instanceof Window) {
            ((Window) parent).setAlwaysOnTop(ontop);
        }

        if (isMultiSelectionEnabled()) {
            if (selectedFiles != null && selectedFiles.size() > 0) {
                currentDirectory = selectedFiles.get(0).getParentFile();
                preferences.put("CurrentDirectory", currentDirectory.getAbsolutePath());
                return JFileChooser.APPROVE_OPTION;
            } else {
                return JFileChooser.CANCEL_OPTION;
            }
        } else {
            if (selectedFile != null) {
                currentDirectory = selectedFiles.get(0).getParentFile();
                preferences.put("CurrentDirectory", currentDirectory.getAbsolutePath());
                return JFileChooser.APPROVE_OPTION;
            } else {
                return JFileChooser.CANCEL_OPTION;
            }
        }
    }

    @Override
    public int showSaveDialog(Component parent) throws HeadlessException {
        return show(parent, CommonDialogs.Type.SAVE);
    }
    
    @Override
    public int showDialog(Component parent, String approveButtonText) {
        if (!FX_AVAILABLE) {
            return super.showDialog(parent, approveButtonText);
        }
        return showOpenDialog(parent);
    }

    @Override
    public void setCurrentDirectory(File currentDirectory) {
        if (FX_AVAILABLE) {
            if (currentDirectory == null) {
                currentDirectory = new File(preferences.get("CurrentDirectory", null));
            }
            this.currentDirectory = currentDirectory;
        } else
            super.setCurrentDirectory(currentDirectory);
    }

    @Override
    public File getCurrentDirectory() {
        if (FX_AVAILABLE) {
            if (currentDirectory == null) {
                if (selectedFile != null)
                    return selectedFile.getParentFile();
            }
            return currentDirectory;
        } else
            return super.getCurrentDirectory();
    }

    @Override
    public File[] getSelectedFiles() {
        if (!FX_AVAILABLE) {
            return super.getSelectedFiles();
        }
        if (selectedFiles == null) {
            return new File[0];
        }
        return selectedFiles.toArray(new File[selectedFiles.size()]);
    }

    @Override
    public File getSelectedFile() {
        if (!FX_AVAILABLE) {
            return super.getSelectedFile();
        }
        return selectedFile;
    }

    @Override
    public void setSelectedFiles(File[] selectedFiles) {
        if (!FX_AVAILABLE) {
            super.setSelectedFiles(selectedFiles);
            return;
        }

        if (selectedFiles == null || selectedFiles.length == 0) {
            setSelectedFile(null);
        } else {
            setSelectedFile(selectedFiles[0]);
            this.selectedFiles = new ArrayList<>(Arrays.asList(selectedFiles));
        }
    }

    @Override
    public void setSelectedFile(File file) {
        if (!FX_AVAILABLE) {
            super.setSelectedFile(file);
        } else {
            selectedFile = file;
            if (file != null && file.isAbsolute())
                setCurrentDirectory(file.getParentFile());
        }
    }

    @Override
    public void changeToParentDirectory() {
        if (!FX_AVAILABLE) {
            super.changeToParentDirectory();
        }
    }

    public void setFileFilter(FileFilter filter) {
        if (!FX_AVAILABLE) {
            super.setFileFilter(filter);
        } else {
            addChoosableFileFilter(filter);
        }
    }

    @Override
    public void addChoosableFileFilter(FileFilter filter) {

        if (!FX_AVAILABLE) {
            super.addChoosableFileFilter(filter);
            return;
        }

        if (filter.getClass().equals(FileNameExtensionFilter.class)) {
            FileNameExtensionFilter f = (FileNameExtensionFilter) filter;

            List<String> ext = new ArrayList<>();
            for (String extension : f.getExtensions()) {
                ext.add(extension.replaceAll("^\\*?\\.?(.*)$", "*.$1"));
            }
            fixAddFilter(f.getDescription(), ext);
        } else {
            if (!filter.getClass().getPackage().getName().equals("javax.swing.plaf.basic"))
                throw new UnsupportedOperationException("filter must is FileNameExtensionFilter");
        }
    }

    private void fixAddFilter(String desc, List<String> ext) {
        boolean has = false;
        for (CommonDialogs.ExtensionFilter filter : filters) {
            if (filter.getDescription().equals(desc) && filter.getExtensions().equals(ext)) {
                has = true;
                break;
            }
        }
        if (!has)
            filters.add(new CommonDialogs.ExtensionFilter(desc, ext));
    }

    @Override
    public void setAcceptAllFileFilterUsed(boolean bool) {
        if (!FX_AVAILABLE) {
            super.setAcceptAllFileFilterUsed(bool);
            return;
        }

        if (bool) {
            List<String> ext = new ArrayList<>();
            ext.add("*.*");
            fixAddFilter("????", ext);//todo ???
        } else {
            for (CommonDialogs.ExtensionFilter filter : filters) {
                if (filter.getExtensions().size() == 1
                        && filter.getExtensions().contains("*.*")) {
                    filters.remove(filter);
                }
            }
        }
    }
}