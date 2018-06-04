LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := myLib
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := myLib.cpp

include $(BUILD_SHARED_LIBRARY)