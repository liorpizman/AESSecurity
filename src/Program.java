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
        String[] newArgs = validateArguments(args);
        int i = 1;
        switch (newArgs[0]) {
            case "-e":
                EncryptionMode(i, newArgs);
                break;
            case "-d":
                DecryptionMode(i, newArgs);
                break;
            case "-b":
                HackMode(i, newArgs);
                break;
        }
    }

    /**
     * Mode for decryption
     *
     * @param i    current index
     * @param args arguments from the user
     */
    private static void DecryptionMode(int i, String[] args) {
        String cipherText = "", outPathDecrypt = "", keyPath = "";
        while (i < args.length) {
            switch (args[i]) {
                case "-i":
                    if ((i + 1) < args.length)
                        cipherText = args[i + 1];
                    break;
                case "-k":
                    if ((i + 1) < args.length)
                        keyPath = args[i + 1];
                    break;
                case "-o":
                    if ((i + 1) < args.length)
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
     * @param i    current index
     * @param args arguments from the user
     */
    private static void EncryptionMode(int i, String[] args) {
        String plainText = "", outPathEncrypt = "", keyPath = "";
        while (i < args.length) {
            switch (args[i]) {
                case "-k":
                    if ((i + 1) < args.length)
                        keyPath = args[i + 1];
                    break;
                case "-i":
                    if ((i + 1) < args.length)
                        plainText = args[i + 1];
                    break;
                case "-o":
                    if ((i + 1) < args.length)
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
     * @param i    current index
     * @param args arguments from the user
     */
    private static void HackMode(int i, String[] args) {
        String plainText = "", outPathHack = "", cipherText = "";
        while (i < args.length) {
            switch (args[i]) {
                case "-m":
                    if ((i + 1) < args.length)
                        plainText = args[i + 1];
                case "-c":
                    if ((i + 1) < args.length)
                        cipherText = args[i + 1];
                case "-o":
                    if ((i + 1) < args.length)
                        outPathHack = args[i + 1];
            }
            i++;
        }
        HackAES hackAES = new HackAES(plainText, outPathHack, cipherText);
        hackAES.RunHack();
    }

    /**
     * Validates legal arguments from the user
     *
     * @param args arguments in the input
     * @return legal arguments
     */
    private static String[] validateArguments(String[] args) {
        int argSize = args.length;
        String[] commandArgs = new String[argSize];
        StringBuilder updatedPath = new StringBuilder();
        int _new = 0, _old = 0;
        while (_old < argSize) {
            if (isFlag(args[_old])) {
                if (updatedPath.toString().isEmpty()) {
                    commandArgs[_new++] = args[_old];
                } else {
                    commandArgs[_new++] = updatedPath.toString();
                    commandArgs[_new++] = args[_old];
                }
                updatedPath = new StringBuilder();
            } else {
                switch (updatedPath.length()) {
                    case 0:
                        updatedPath.append(args[_old]);
                        break;
                    default:
                        updatedPath.append(" " + args[_old]);
                        break;
                }
            }
            _old++;
        }
        String lastArg = updatedPath.toString();
        // check whether we should update last argument from the user
        if (_new < argSize && !lastArg.isEmpty()) {
            commandArgs[_new++] = lastArg;
        }
        return removeNullElements(commandArgs, _new);
    }

    /**
     * Checks whether current argument is a legal flag
     *
     * @param f current argument
     * @return true if is a legal flag, otherwise , false
     */
    private static boolean isFlag(String f) {
        return f.length() == 2 && f.charAt(0) == '-';
    }

    /**
     * Remove all null elements in array
     *
     * @param arr  input array
     * @param size size of new array
     * @return array without null elements
     */
    private static String[] removeNullElements(String[] arr, int size) {
        String[] newArr = new String[size];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (arr[i] != null)
                newArr[j++] = arr[i];
        }
        return newArr;
    }
}
