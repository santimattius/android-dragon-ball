# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# =============================================================================
# R8 DEMO — Estado 02: Regla quirúrgica (@SerializedName únicamente)
# Después de correr el R8 Configuration Analyzer, identificamos que Gson solo
# necesita: los campos anotados con @SerializedName (para mapear JSON keys) y
# los constructores (para instanciar las clases). El resto puede ser shrunk/
# obfuscated por R8 libremente.
# Ver: r8-demo/02-surgical-rule
# =============================================================================
-keepclassmembers class com.santimattius.basic.skeleton.core.data.** {
    @com.google.gson.annotations.SerializedName <fields>;
    <init>(...);
}
