//#include "MainJNI.h"
#include "com_kayzio_observer_model_DLLCall.h"
#include <jni.h>
#include <stdio.h>
#include <Windows.h>
#include <sstream>
#include <iostream>
//#include <math.h>
//#include <vector>
//#include <algorithm>
#include <tlhelp32.h>
#include <tchar.h>
//#include <cstdio>

using namespace std;

TCHAR clientGetterDLLName[] = _T("client.dll");
DWORD clientGetter = 0;

const DWORD eBase = 0x4A8D1AC;
const DWORD locoOFF = 0xAAFD7C;

const DWORD eLoopDis = 0x10;
const DWORD tOffs = 0xF4;

const DWORD crossOff = 0xB2A4;
const DWORD jOff = 0x100;

int mainSize = 18;

LPCTSTR wName = "Counter-Strike: Global Offensive";
HWND hGameWindow = NULL; 
DWORD dwProcID = NULL;
HANDLE hProcHandle = NULL;

struct pList {
	DWORD localP;
	int cId;
	int pT;
	void infoForP(){
		ReadProcessMemory(hProcHandle, (PBYTE*)(clientGetter + locoOFF), &localP, sizeof(DWORD), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(localP + crossOff), &cId, sizeof(int), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(localP + tOffs), &pT, sizeof(int), 0);
	}
}pList;

struct testList{
	DWORD bE;
	int eT;
	void infoForE(int i){
		ReadProcessMemory(hProcHandle, (PBYTE*)(clientGetter + eBase + (i * eLoopDis)), &bE, sizeof(DWORD), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(bE + tOffs), &eT, sizeof(int), 0);
	}
}testList[18];

int functionF(){
	if(hProcHandle != NULL && clientGetter != 0){
		pList.infoForP();

		for(int i = 0; i < mainSize; i++){
			testList[i].infoForE(i);
		}
		if(pList.cId == 0){
			return 0;
		}
		if(testList[pList.cId-1].eT == pList.pT){
			return 0;
		}
		if(pList.cId > mainSize){
			return 0;
		}
		return 1;
	}

	return 0;
}

DWORD_PTR functionF(DWORD dwProcID, TCHAR *szModuleName, int z, int a, int b){
    HANDLE hiddenSnapShitForever = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32, dwProcID);
    DWORD_PTR baseModuleAddressHiddenAwayForever = 0;
    if (hiddenSnapShitForever != INVALID_HANDLE_VALUE){
        MODULEENTRY32 moduleHiddenEntryCant;
        moduleHiddenEntryCant.dwSize = sizeof(MODULEENTRY32);
        if (Module32First(hiddenSnapShitForever, &moduleHiddenEntryCant)){
            do{
                if (_tcsicmp(moduleHiddenEntryCant.szModule, szModuleName) == 0){
                    baseModuleAddressHiddenAwayForever = (DWORD_PTR)moduleHiddenEntryCant.modBaseAddr;
                    break;
                }
            } while (Module32Next(hiddenSnapShitForever, &moduleHiddenEntryCant));
        }
        CloseHandle(hiddenSnapShitForever);
    }
    return baseModuleAddressHiddenAwayForever;
}

//-----------------------------THIS IS THE MAIN PART-----------------------------

// Initialize everything
JNIEXPORT jint JNICALL Java_com_kayzio_observer_model_DLLCall_functionF__I (JNIEnv *env, jobject thisObj, jint z) {
    hGameWindow = NULL;
    while(hGameWindow == NULL){
   		hGameWindow = FindWindow(NULL, wName); 
	}
	if(hGameWindow){
		GetWindowThreadProcessId(hGameWindow, &dwProcID);
		if(dwProcID != 0){
			hProcHandle = OpenProcess(PROCESS_ALL_ACCESS, FALSE, dwProcID);
			if(hProcHandle == INVALID_HANDLE_VALUE || hProcHandle == NULL){
				return 1;
			}else{
				clientGetter = functionF(dwProcID, _T("client.dll"), 0, 0, 0);
			}
		}else{
			return 1;
		}
	}else{
		return 1;
	}
	return 0;
}

// Check for window
JNIEXPORT jint JNICALL Java_com_kayzio_observer_model_DLLCall_functionF__II (JNIEnv *env, jobject thisObj, jint z, jint a) {
	HWND hwnd = FindWindow(NULL, wName);
	if(hwnd){
		CloseHandle(hwnd);
		return 0;
	}else{
		CloseHandle(hwnd);
		return 1;
	}
}

// Close handles
JNIEXPORT void JNICALL Java_com_kayzio_observer_model_DLLCall_functionF__III (JNIEnv *env, jobject thisObj, jint z, jint a, jint b) {
    if(hProcHandle != NULL || hGameWindow != NULL){
    	CloseHandle(hProcHandle);
		CloseHandle(hGameWindow);
    }
}

// CHECK IF AIMING AT ENEMY
JNIEXPORT jint JNICALL Java_com_kayzio_observer_model_DLLCall_functionF__IIII (JNIEnv *env, jobject thisObj, jint z, jint a, jint b, jint c) {
   	int reuturnValue = functionF();
    //cout << "SHOOT -> " << reuturnValue << endl;
    return reuturnValue;
}

// GET CROSSHAIR ID (FOR TESTING)
JNIEXPORT jint JNICALL Java_com_kayzio_observer_model_DLLCall_functionF__IIIII (JNIEnv *env, jobject thisObj, jint z, jint a, jint b, jint c, jint d) {
	if(hProcHandle != NULL && clientGetter != 0){
		DWORD localP;
		int cId;
		ReadProcessMemory(hProcHandle, (PBYTE*)(clientGetter + locoOFF), &localP, sizeof(DWORD), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(localP + crossOff), &cId, sizeof(int), 0);
	    return cId;
	}
}

/*

// CHECK IF JUMPING
JNIEXPORT jint JNICALL Java_com_kayzio_observer_model_DLLCall_functionF__IIIIII (JNIEnv *env, jobject thisObj, jint z, jint a, jint b, jint c, jint d, jint e) {
	if(hProcHandle != NULL && clientGetter != 0){
		DWORD localP;
		int jId;
		ReadProcessMemory(hProcHandle, (PBYTE*)(clientGetter + locoOFF), &localP, sizeof(DWORD), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(localP + jOff), &jId, sizeof(int), 0);
	    return jId;
	}
}

*/

// WE ONLY WANT THE ^ TRIGGERBOT FOR NOW
/*
JNIEXPORT jint JNICALL Java_Controller_functionF__IIIII (JNIEnv *env, jobject thisObj, jint z, jint a, jint b, jint c, jint d) {
	if(hProcHandle != NULL && clientGetter != 0){
		DWORD localP;
		int cId;
		ReadProcessMemory(hProcHandle, (PBYTE*)(clientGetter + locoOFF), &localP, sizeof(DWORD), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(localP + crossOff), &cId, sizeof(int), 0);
	    return cId;
	}
}

JNIEXPORT jint JNICALL Java_Controller_functionF__IIIIII (JNIEnv *env, jobject thisObj, jint z, jint a, jint b, jint c, jint d, jint e) {
	if(hProcHandle != NULL && clientGetter != 0){
		DWORD localP;
		int pT;
		ReadProcessMemory(hProcHandle, (PBYTE*)(clientGetter + locoOFF), &localP, sizeof(DWORD), 0);
		ReadProcessMemory(hProcHandle, (PBYTE*)(localP + tOffs), &pT, sizeof(int), 0);
	    return pT;
	}
}
*/