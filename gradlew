#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available path length for directory names on Windows.
# The `cygpath` command is available in Cygwin and MSYS2.
if command -v cygpath > /dev/null; then
    # For Cygwin, switch paths to Windows format before running java
    if [ $(uname) = "CYGWIN" ]; then
        CYGWIN=true
    fi
fi

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Don't use `readlink -f` because it's not POSIX compliant and doesn't work on OS X.
# Follow symlinks until we find the real source directory of this script.
source_dir() {
    local SOURCE="$1"
    while [ -h "$SOURCE" ]; do
        local DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
        SOURCE="$(readlink "$SOURCE")"
        [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE"
    done
    ( cd -P "$( dirname "$SOURCE" )" && pwd )
}
APP_HOME="$(source_dir "$0")"

# Split up the JVM options string into an array, following normal shell quoting and substitution rules.
# This is a bit complex because we need to handle scenarios where the options are not quoted, or have spaces.
eval set -- "$DEFAULT_JVM_OPTS" "$JAVA_OPTS" "$GRADLE_OPTS"
JVM_OPTS=("$@")

# Escape the arguments for Cygwin.
if [ "$CYGWIN" = "true" ]; then
    for i in "${!JVM_OPTS[@]}"; do
        JVM_OPTS[$i]="$(cygpath --windows -- "${JVM_OPTS[$i]}")"
    done
fi

# Add the jar to the classpath.
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Execute Gradle.
exec "$JAVACMD" "${JVM_OPTS[@]}" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
