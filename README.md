# AESsecurity
###### AES 3* Encryption and Decryption Classwork

## Encryption and Decryption Interface

o 	–e : instruction to encrypt the input file <br/>
o 	–d: instruction to decrypt the input file <br/>
o   –k <path>: path to the keys, the key should be 384 bit (128*3) for 〖AES〗_3^*. and should be divided into 3 separate keys. <br/>
o 	–i <input file path>: a path to a file we want to encrypt/decrypt <br/>
o 	–o <output file path>: a path to the output file <br/>
o	  Usage: Java –jar aes.jar -e/-d –k <path-to-key-file > -i <path-to-input-file> -o <path-to-output-file> <br/><br/>

## Hack Tool Interface

o  –b : instruction to break the encryption algorithm <br/>
o	 –m <path>: denotes the path to the plain-text message <br/>
o  –c <path>: denotes the path to the cipher-text message <br/>
o	 –o <path>: a path to the output file with the key(s) found. <br/>
o  Usage: Java –jar aes.jar -b –m <path-to-message> –c <path-to-cipher> -o < output-path><br/>

