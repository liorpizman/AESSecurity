import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents Decryption
 */
public class AESdecrypt extends AmainAES implements IDecrypt {

    /**
     * Fields of Decryption
     */
    private byte[] OriginalMessages;
    private byte[] m_Cipher;

    /**
     * Constructor of AES algorithm to decryption
     *
     * @param cipherText cipher path
     * @param out        output path
     * @param keyPath    keys path
     */
    public AESdecrypt(String cipherText, String out, String keyPath) {
        C = new ConvertByte();
        CipherKey1 = new byte[4][Nb];
        CipherKey2 = new byte[4][Nb];
        CipherKey3 = new byte[4][Nb];
        outPath = out;
        SplitToFields(keyPath, cipherText);
    }

    /**
     * Splits byte arrays to keys and message fields of current class
     * @param keyPath keys path
     * @param cipherText cipher path
     */
    @Override
    protected void SplitToFields(String keyPath, String cipherText) {
        byte[] key;
        try {
            Path keyLocation = Paths.get(keyPath);
            key = Files.readAllBytes(keyLocation);

            SeparateKeys(key);

            Path msgLocation = Paths.get(cipherText);
            m_Cipher = Files.readAllBytes(msgLocation);
            OriginalMessages = new byte[m_Cipher.length];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to run the decryption
     */
    @Override
    public void Decrypt() {
        byte[][] state = new byte[4][Nb];
        byte[] currState = new byte[blockBytes];
        int startIndex = 0;
        while (startIndex < m_Cipher.length) {
            InitialCipherMsg(currState, startIndex);
            C.Append(state, currState);

            state = AddRoundKey(state, CipherKey3);
            ShiftRows(state);

            state = AddRoundKey(state, CipherKey2);
            ShiftRows(state);

            state = AddRoundKey(state, CipherKey1);
            ShiftRows(state);

            UpdateMessage(startIndex, state);
            startIndex += 16;
        }
        WriteOutput(OriginalMessages, outPath);
    }

    /**
     * Updates results of AES algorithm
     * @param startIndex start index for update
     * @param state      current byte array
     */
    @Override
    public void UpdateMessage(int startIndex, byte[][] state) {
        byte[] msg = new byte[blockBytes];
        C.Reduce(msg, state);
        for (int i = 0; i < blockBytes; i++) {
            OriginalMessages[startIndex + i] = msg[i];
        }
    }

    /**
     * Shift right of Rows of matrix by steps
     *
     * @param state current byte array
     */
    @Override
    protected void ShiftRows(byte[][] state) {  /* Shift right */
        byte[] tmp = new byte[4];
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                tmp[(j + i) % 4] = state[i][j];
            for (int k = 0; k < 4; k++)
                state[i][k] = tmp[k];
        }
    }

    /**
     * Updates current state from start index
     *
     * @param currState  current byte array
     * @param startIndex start index to update
     */
    @Override
    public void InitialCipherMsg(byte[] currState, int startIndex) {
        for (int i = 0; i < blockBytes; i++)
            currState[i] = m_Cipher[startIndex + i];
    }
}
