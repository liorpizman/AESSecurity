/**
 * This interface represents the methods of each decryption class
 */
public interface IDecrypt {

    void Decrypt();

    void UpdateMessage(int startIndex, byte[][] state);

    void InitialCipherMsg(byte[] currState, int startIndex);

}
