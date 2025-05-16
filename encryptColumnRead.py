#! /usr/bin/env python3

# read the data created by EncryptColumnTest java app and decrypt the user name

import mysql.connector
import os
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding
import subprocess
import base64

def cryptoEncode(ba):
    return base64.b64encode(ba)

def cryptoDecode(ba):
    return base64.b64decode(ba, validate = True)

def readKey():
    keyFile = os.path.expanduser('~') + "/.blazartech/encryptColumn.key"
    with open(keyFile, "r") as kf:
        key = cryptoDecode(kf.readline().rstrip())
        print("key length = " + str(len(key)))
        return key

# read the decryption key
key = readKey()

# create the cipher
cryptoFileCipher = Cipher(algorithms.AES(key), modes.ECB())

# get the decrypter
decryptor = cryptoFileCipher.decryptor()

def decryptName(person):
    return decryptor.update(cryptoDecode(person[1])).decode()

def encryptName(personName):
    encryptor = cryptoFileCipher.encryptor()
    padder = padding.PKCS7(algorithms.AES.block_size).padder()
    paddedName = padder.update(personName.encode("utf-8")) + padder.finalize()
    encrypted = cryptoEncode(encryptor.update(paddedName) + encryptor.finalize()).decode()
    return encrypted

def getUserPassword(dbUser):
    cmd = "pass Database/MySQL/local/" + dbUser
    process = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True, text=True)
    output, error = process.communicate()
    return output.rstrip()

# connect to the DB
dbUser = "scott"
dbPassword = getUserPassword(dbUser)
mydb = mysql.connector.connect(
  host="localhost",
  user=dbUser,
  password=dbPassword,
  database="EncryptedColumnTest"
)

cursor = mydb.cursor()
cursor.execute("select * from Person")
result = cursor.fetchall()

for p in result:
    print("person " + str(p[0]) + " name = " + decryptName(p))

# insert a new person
def addPerson(personName):
    insertCursor = mydb.cursor()
    encryptedNameText = encryptName(personName)
    print("encrypted name = " + encryptedNameText)
    sql = "insert into Person (Name) values (%s)"
    args = [encryptedNameText]
    insertCursor.execute(sql, args)
    mydb.commit() 
    print("inserted person with ID " + str(insertCursor.lastrowid))

addPerson("Eddie van Halen")
addPerson("Alex van Halen")
addPerson("Michael Anthony")
addPerson("David Lee Roth")
addPerson("Sammy Hagar")
addPerson("Gary Cherone")

