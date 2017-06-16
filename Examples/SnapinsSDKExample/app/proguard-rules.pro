############### ALL ###############

-dontwarn okio.**

############### SOS ###############
# You can delete this section if you are not using SOS

-dontwarn lombok.**

# Our components are initialized using reflection and can appear to be unused
-keepclassmembers class * implements com.salesforce.android.sos.component.Component

# ------------------ Eventbus Begin --------------------------
# The onEvent methods are called from the EventBus library and can appear unused.
-keepclassmembers class com.salesforce.android.sos.** {
    public void onEvent(...);
}
# ------------------ Eventbus End ----------------------------

# ------------------ Opentok Begin ---------------------------
# OpenTok cannot handle any code stripping for optimization.
-keep class com.opentok.** { *; }
-keep class org.webrtc.** { *; }
# ------------------ Opentok End -----------------------------

# ------------------ Gson Begin ------------------------------
# Preserve the special static methods that are required in all enumeration classes.
# We use these predominantly for serializing enums with Gson.
-keepclassmembers enum com.salesforce.android.sos.** {
    **[] $VALUES;
    public *;
}
# ------------------ Gson End --------------------------------


###### SALESFORCE MOBILE SDK ######
# You can delete this section if you are not using Knowledge or Cases

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Required for user login to work on KitKat devices
-keepclassmembernames class com.salesforce.androidsdk.auth.SalesforceTLSSocketFactory { *; }

# For sqlcipher in Mobile SDK
-keep class net.sqlcipher.** { *; }
-dontwarn net.sqlcipher.**