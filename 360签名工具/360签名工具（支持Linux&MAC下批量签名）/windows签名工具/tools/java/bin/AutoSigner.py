# -*- coding: UTF-8 -*-
#!/usr/bin/env python
__author__ = 'zhangdongyi'

import os,sys
import subprocess

#jarsigner.exe -verbose -keystore D:\test.keystore -storepass "12341234" -keypass "12341234" -signedjar D:\DeskTop\1234signed.apk D:\DeskTop\1234.apk "test.keystore"
#  D:\test.keystore 12341234 12341234 D:\DeskTop\1234.apk D:\DeskTop\1234signed.apk test.keystore
def SignApk(keystore_path, store_pass, key_pass, apk_path, signed_path, key_alias):
    cmd_list = ['jarsigner.exe', '-verbose', '-keystore', keystore_path,
                '-storepass', store_pass, '-keypass', key_pass, '-signedjar',signed_path, apk_path, key_alias]
    try:
        process = subprocess.Popen(cmd_list ,stdout= subprocess.PIPE, stderr= subprocess.PIPE, shell=False)
        out,err = process.communicate()
        print str(out)
    except:
        print "call jarsigner failed"
        print str(out)
        sys.exit(-2)
    return 1

#zipalign.exe -f -v 4 D:\DeskTop\1234signed.apk D:\DeskTop\1234signedAligned.apk
def zipalign(in_apk, out_apk):
    cmd_list = ['zipalign.exe', '-f', '-v', '4', in_apk, out_apk]
    try:
        process = subprocess.Popen(cmd_list, stdout= subprocess.PIPE, stderr= subprocess.PIPE, shell=False)
        out,err = process.communicate()
        #print str(out)
    except:
        print "call ziplign failed"
        sys.exit(-3)
    return 1

#java -jar "SignApk.jar" "public\media.x509.pem" "public\media.pk8" "getinfoed.apk" "getinfoed.apk.Signed"
def KeySign(cert_pem, private_key, in_apk, out_apk):
    cmd_list = ['java', '-jar', 'SignApk.jar', cert_pem, private_key, in_apk, out_apk]
    try:
        process = subprocess.Popen(cmd_list, stdout= subprocess.PIPE, stderr= subprocess.PIPE, shell=False)
        out,err = process.communicate()
        print str(out)
    except:
        sys.exit(-5)

'''
#call args
    1 keystore_path
    2 store_pass
    3 key_pass
    4 apk_input_path
    5 apk_output_path
    6 alias_name
    7 aligned_path
AutoSigner.py D:\test.keystore 12341234 12341234 D:\DeskTop\1234.apk D:\DeskTop\1234signed.apk test.keystore d:\desktop\aligned.apk

'''
if __name__ == '__main__':
    if len(sys.argv) != 8:
        print "please input Correct"
        sys.exit(-1)
    keystore_path=sys.argv[1]
    store_pass=sys.argv[2]
    key_pass=sys.argv[3]
    apk_input_path=sys.argv[4]
    apk_output_path=sys.argv[5]
    alias_name=sys.argv[6]
    aligned_path=sys.argv[7]
    nret = SignApk(keystore_path, store_pass, key_pass, apk_input_path, apk_output_path,alias_name)
    if nret == 1:
        nret = zipalign(apk_output_path, aligned_path)
        if nret == 1:
            os.remove(apk_output_path)
            sys.exit(1)
    else:
        sys.exit(-1)





