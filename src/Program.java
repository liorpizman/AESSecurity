/**
 * This class represents the User Interface with the machine to run AES3 algorithm
 */
public class Program {

    /**
     * Method to run the project
     *
     * @param args flags chosen by the user
     */
    public static void main(String[] args) {
        String[] flags = args;
        int i = 1;
        switch (args[0]) {
            case "-e":
                EncryptionMode(i, args, flags);
                break;
            case "-d":
                DecryptionMode(i, args, flags);
                break;
            case "-b":
                HackMode(i, args, flags);
                break;
        }
    }

    /**
     * Mode for decryption
     *
     * @param i     current index
     * @param args  arguments from the user
     * @param flags flags chosen by the user
     */
    private static void DecryptionMode(int i, String[] args, String[] flags) {
        String cipherText = "", outPathDecrypt = "", keyPath = "";
        while (i < flags.length) {
            switch (args[i]) {
                case "-i":
                    if ((i + 1) < flags.length)
                        cipherText = args[i + 1];
                    break;
                case "-k":
                    if ((i + 1) < flags.length)
                        keyPath = args[i + 1];
                    break;
                case "-o":
                    if ((i + 1) < flags.length)
                        outPathDecrypt = args[i + 1];
                    break;
            }
            i++;
        }
        AESdecrypt decrypt = new AESdecrypt(cipherText, outPathDecrypt, keyPath);
        decrypt.Decrypt();
    }

    /**
     * Mode for encryption
     *
     * @param i     current index
     * @param args  arguments from the user
     * @param flags flags chosen by the user
     */
    private static void EncryptionMode(int i, String[] args, String[] flags) {
        String plainText = "", outPathEncrypt = "", keyPath = "";
        while (i < flags.length) {
            switch (args[i]) {
                case "-k":
                    if ((i + 1) < flags.length)
                        keyPath = args[i + 1];
                    break;
                case "-i":
                    if ((i + 1) < flags.length)
                        plainText = args[i + 1];
                    break;
                case "-o":
                    if ((i + 1) < flags.length)
                        outPathEncrypt = args[i + 1];
                    break;
            }
            i++;
        }
        AESencrypt encrypt = new AESencrypt(plainText, outPathEncrypt, keyPath);
        encrypt.Encrypt();
    }

    /**
     * Mode for hacking AES3 algorithm
     *
     * @param i     current index
     * @param args  arguments from the user
     * @param flags flags chosen by the user
     */
    private static void HackMode(int i, String[] args, String[] flags) {
        String plainText = "", outPathHack = "", cipherText = "";
        while (i < flags.length) {
            switch (args[i]) {
                case "-m":
                    if ((i + 1) < flags.length)
                        plainText = args[i + 1];
                case "-c":
                    if ((i + 1) < flags.length)
                        cipherText = args[i + 1];
                case "-o":
                    if ((i + 1) < flags.length)
                        outPathHack = args[i + 1];
            }
            i++;
        }
        HackAES hackAES = new HackAES(plainText, outPathHack, cipherText);
        hackAES.RunHack();
    }
}

