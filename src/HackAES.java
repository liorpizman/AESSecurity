import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents Hack algorithm of AES
 */
public class HackAES extends AHack implements IBreaker {

    /**
     * Constructor of Hack Algorithm of AES
     *
     * @param plainText  message path
     * @param out        output path
     * @param cipherPath cipher path
     */
    public HackAES(String plainText, String out, String cipherPath) {
        CipherKey1 = new byte[4][Nb];
        CipherKey2 = new byte[4][Nb];
        CipherKey3 = new byte[4][Nb];
        outPath = out;
        C = new ConvertByte();
        GetMessageAndCipher(plainText, cipherPath);
    }

    /**
     * Method to run the algorithm of hacking AES
     */
    @Override
    public void RunHack() {
        byte[][] M = new byte[4][Nb], tmpKey;
        byte[][] Cipher = new byte[4][Nb];
        CreateRandomKey();                      /* creates random CipherKey1 */
        CipherKey2 = ShiftRows(CipherKey1);
        C.Append(M, m_Msg);
        tmpKey = ShiftRows(M);
        tmpKey = ShiftRows(tmpKey);
        tmpKey = ShiftRows(tmpKey);
        C.Append(Cipher, m_Cipher);
        CipherKey3 = XorWithCipher(tmpKey, Cipher);
        WriteOutput(CombineKeys(), outPath);
    }

    /**
     * Combines three cipher keys to one byte array
     *
     * @return byte array of cipher key
     */
    @Override
    protected byte[] CombineKeys() {
        byte[] k1 = new byte[blockBytes];
        byte[] k2 = new byte[blockBytes];
        byte[] k3 = new byte[blockBytes];
        C.Reduce(k1, CipherKey1);
        C.Reduce(k2, CipherKey2);
        C.Reduce(k3, CipherKey3);
        byte[] cipherKey = new byte[blockBytes * 3];
        MergeByteArray(cipherKey, k1, 0);
        MergeByteArray(cipherKey, k2, blockBytes);
        MergeByteArray(cipherKey, k3, blockBytes * 2);
        return cipherKey;
    }

    /**
     * Merges two byte arrays
     *
     * @param cipherKey  first byte array
     * @param key        second byte array
     * @param startIndex start index for the merge
     */
    @Override
    protected void MergeByteArray(byte[] cipherKey, byte[] key, int startIndex) {
        for (int i = 0; i < key.length; i++) {
            cipherKey[startIndex + i] = key[i];
        }
    }

    /**
     * Separates message and cipher for future use
     *
     * @param plainText  message path
     * @param cipherPath cipher path
     */
    @Override
    public void GetMessageAndCipher(String plainText, String cipherPath) {
        try {
            Path cipherLocation = Paths.get(cipherPath);
            m_Cipher = Files.readAllBytes(cipherLocation);

            Path msgLocation = Paths.get(plainText);
            m_Msg = Files.readAllBytes(msgLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates random key for first cipher key
     */
    @Override
    public void CreateRandomKey() {
        byte[] bytes;
        for (int i = 0; i < 4; i++) {
            bytes = CreateRandomBytesArray();
            for (int j = 0; j < 4; j++) {
                CipherKey1[i][j] = bytes[j];
            }
        }
    }

    /**
     * XOR operation on two dimensional byte arrays
     *
     * @param tmpKey first byte array
     * @param cipher second byte array
     * @return
     */
    @Override
    protected byte[][] XorWithCipher(byte[][] tmpKey, byte[][] cipher) {
        byte[][] roundKey = new byte[Nb][Nb];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                roundKey[j][i] = (byte) (tmpKey[j][i] ^ cipher[j][i]);
        return roundKey;
    }

}
