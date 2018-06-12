package org.jabref.gui.openoffice;

import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jabref.logic.openoffice.OpenOfficePreferences;
import org.jabref.testutils.TestUtils;
import org.jabref.testutils.category.GUITest;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

@Category(GUITest.class)
class DetectOpenOfficeInstalationTest extends AssertJSwingJUnitTestCase {

    private FrameFixture frameFixture;
    private final JDialog parent = new JDialog();
    private final OpenOfficePreferences preferences = new OpenOfficePreferences(null, null, null, null, null, null, null, null);
    private final DetectOpenOfficeInstallation detect = new DetectOpenOfficeInstallation(parent, preferences);

    @BeforeClass
    public static void before() {
        FailOnThreadViolationRepaintManager.uninstall();
    }

    @Override
    protected void onSetUp() {
        TestUtils.initJabRef();
        frameFixture = WindowFinder.findFrame(JFrame.class).withTimeout(15, TimeUnit.SECONDS).using(robot());
    }

    @Override
    protected void onTearDown() {
        frameFixture.close();
        frameFixture = null;

        TestUtils.closeJabRef();
    }


    @Test
    void testIsInstalled() {

        boolean isInstalled = detect.isInstalled();

        Assert.assertFalse(isInstalled);
    }


    @Test
    void testRun() {

        detect.run();

        Assert.assertFalse(detect.getFoundPaths());

    }

}
