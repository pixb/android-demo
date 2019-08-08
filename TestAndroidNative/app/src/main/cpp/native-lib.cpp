#include <jni.h>
#include <string>


extern "C" {
JNIEXPORT jstring JNICALL
Java_com_pix_testandroidnative_MainActivity_stringFromHello(JNIEnv *env, jobject instance) {

    // TODO
    std::string helloWorld = "hello world!";
    return env->NewStringUTF(helloWorld.c_str());
}


JNIEXPORT jstring JNICALL
Java_com_pix_testandroidnative_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C+++++++";
    return env->NewStringUTF(hello.c_str());
}
}