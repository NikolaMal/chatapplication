#include "nikola_malencic_chatapplication_JNIClass.h"
#include <stdio.h>
#include <cstring>
#include <stdlib.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_nikola_malencic_chatapplication_JNIClass_encryptDecrypt (JNIEnv *env, jobject obj, jstring message)
{
    char *key_string;
    key_string = "This is the key";
    int key_string_size = strlen(key_string);



    const char *msg = env->GetStringUTFChars(message, NULL);
    int msg_size = strlen(msg);


    char* new_msg = (char*)malloc(msg_size*sizeof(char));
    memset(new_msg, 0, msg_size);

    int i, counter;
    counter = 0;
    for (i = 0; i<msg_size; i++)
    {
        if (key_string[counter] != msg[i]){
            char temp = key_string[counter] ^ msg[i];
            new_msg[i] = temp;
            counter++;
            if (counter == key_string_size) {
                counter = 0;
            }
        } else {
            char temp = msg[i];
            new_msg[i] = temp;
            counter++;
            if (counter == key_string_size){
                counter = 0;
            }
        }
    }
    new_msg[i] = 0;

    env->ReleaseStringUTFChars(message, msg);
    jstring output;
    output = env->NewStringUTF(new_msg);
    free(new_msg);
    return output;
}