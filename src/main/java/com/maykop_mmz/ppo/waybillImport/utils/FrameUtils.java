package com.maykop_mmz.ppo.waybillImport.utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.maykop_mmz.ppo.waybillImport.utils.Logging.getCurrentClassName;

/**
 * Created by Eugene Zrazhevsky on 30.10.2016.
 */
public class FrameUtils {
    private static final Logger log = Logger.getLogger(getCurrentClassName());


    private static final Timer timer = new Timer(60, null);

    /**
     * Returns the location of point of window, when it should be on center of the screen.
     *
     * @return Point of <code>Window</code> that is moved to center of the screen.
     * @see java.awt.Component#getLocation
     */
    public static Point getFrameOnCenterLocationPoint(Window window) {
        Dimension size = window.getSize();
        int width = (int) ((Toolkit.getDefaultToolkit().getScreenSize().width / (double) 2) - (size.getWidth() / (double) 2));
        int height = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height / (double) 2) - (size.getHeight() / (double) 2));
        return new Point(width, height);
    }

    /**
     * Finds window on component given.
     *
     * @param component Component where window is located.
     * @return Window that is searched.
     **/
    private static Window findWindow(Component component) {
        if (component == null) {
            return JOptionPane.getRootFrame();
        } else if (component instanceof Window) {
            return (Window) component;
        } else {
            return findWindow(component.getParent());
        }
    }

    /**
     * Shakes window like in MacOS.
     *
     * @param component Component to shake
     */
    public static void shakeFrame(final Component component) {
        final Window window = findWindow(component);

        if (!timer.isRunning()) {
            timer.addActionListener(new ActionListener() {
                final static int maxCounter = 6;
                Point location = window.getLocation();
                int counter = 0;
                int step = 14;

                @SuppressWarnings("ConstantConditions")
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (counter <= 2) {
                        step = 14;
                    } else if (counter > 2 && counter <= 4) {
                        step = 7;
                    } else if (counter > 4 && counter <= 6) {
                        step = 3;
                    }

                    if (counter >= 0) {
                        if (counter <= maxCounter) {
                            counter++;

                            if (location.x < 0 || location.y < 0) {
                                window.setLocation(getFrameOnCenterLocationPoint(window));
                                location = window.getLocation();
                            }
                            if (counter % 2 != 0) {
                                Point newLocation = new Point(location.x + step, location.y);
                                window.setLocation(newLocation);
                            } else {
                                Point newLocation = new Point(location.x - step, location.y);
                                window.setLocation(newLocation);
                            }
                        } else {
                            Point newLocation = new Point(location.x, location.y);
                            window.setLocation(newLocation);

                            counter = 0;
                            timer.removeActionListener(timer.getActionListeners()[0]);
                            timer.stop();
                        }
                    }
                }
            });
            timer.start();
        }
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * Brings windows to front. If some application is already in use, window is shown on top without focus.
     * If user requests switch focus to window and goes back, window will hide.
     *
     * @param window Frame to make on the front of the screen.
     */
    public static void showOnTop(Window window) {
        java.awt.EventQueue.invokeLater(() -> {
            window.setAlwaysOnTop(true);
            window.toFront();
            window.repaint();
            window.setAlwaysOnTop(false);
        });
    }

    /**
     * Sets default font of the application
     *
     * @param font to set
     */
    public static void setUIFont(javax.swing.plaf.FontUIResource font) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }

}
