# R8 rules for the release build.
#
# Deliberately short. Compose, Kotlin and the AndroidX libraries all ship their own consumer rules
# inside their artifacts, so repeating them here is not "safe", it is a copy that goes stale while
# looking authoritative. Add a rule when a release build actually breaks, and say what broke.

# Without these, every release stack trace is a list of obfuscated names with no line numbers, and
# a crash report becomes unactionable. The line tables cost a few KB; a mapping file you cannot
# read costs an afternoon per bug.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Kotlin puts the null-check failures it generates into these methods. Stripping their bodies keeps
# the checks (behaviour is unchanged) while dropping the parameter-name strings they throw with.
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkNotNullParameter(java.lang.Object, java.lang.String);
    static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
}
