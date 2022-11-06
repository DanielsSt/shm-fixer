package tk.navideju.shmfixer;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SHMFixer implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.samsung.android.shealthmonitor")) {
            return;
        }

        XposedBridge.log("Found com.samsung.android.shealthmonitor, trying to hook it");

        try {
            findAndHookMethod(
                "com.samsung.android.shealthmonitor.home.ui.activity.SHealthMonitorSetupActivity",
                lpparam.classLoader,
                "startLibrary",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("beforeHookedMethod startLibrary");
                        param.setResult(null);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("afterHookedMethod startLibrary, calling callFinishAndGoMain");
                        callMethod(param.thisObject, "callFinishAndGoMain");
                    }
                }
            );
        } catch (Exception e) {
            XposedBridge.log("Failed to hook (startLibrary)");
            XposedBridge.log(e);
        }

        try {
            findAndHookMethod(
                "com.samsung.android.shealthmonitor.util.CSCUtils",
                lpparam.classLoader,
                "getCountryCode",
                new XC_MethodReplacement() {
                    @Override
                    protected String replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("ReplaceHookedMethod getCountryCode, returning US");
                        return "US";
                    }
                }
            );
        } catch (Exception e) {
            XposedBridge.log("Failed to hook (getCountryCode)");
            XposedBridge.log(e);
        }
    }
}
