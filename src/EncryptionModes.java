import java.util.Arrays;

class EncryptionModes {
    private byte[] messegeByte;
    private byte[] keyByte;
    private byte[] vectorByte;
    private byte[] keyBlock;
    byte[] encryptedMessageByte;
    private int lenBlock;

    EncryptionModes(String messege, String key, String vector, int lenBlock) {
        keyByte = key.getBytes();
        this.vectorByte = vector.getBytes();
        this.lenBlock = lenBlock;
        messegeByte = processing(messege).getBytes();
        encryptedMessageByte = new byte[messegeByte.length];
        keyBlock = new byte[lenBlock];
    }

    private byte[] encryptionProcedureCBC(byte[] message) {
        byte[] rez = new byte[message.length];
        for (int i = 0; i < message.length; i++) {
            rez[i] = (byte) (message[i] ^ this.keyBlock[i]);
        }
        return rez;
    }

    private String processing(String m) {
        while (m.length() % this.lenBlock != 0) {
            m += " ";
        }
        return m;
    }

    void EncodCBC() {
        byte[] blockMessage = new byte[this.lenBlock];
        byte[] encryptedBlockMessage = new byte[this.lenBlock];
        for (int i = 0; i < this.messegeByte.length / this.lenBlock; i++) {
            for (int j = 0; j < this.lenBlock; j++) {
                blockMessage[j] = this.messegeByte[j + i * this.lenBlock];
                keyBlock[j] = this.keyByte[(j + i * this.lenBlock) % this.keyByte.length];
            }
            encryptedBlockMessage = encodeCBC(blockMessage);
            System.arraycopy(encryptedBlockMessage, 0, vectorByte, 0, encryptedBlockMessage.length);
            //this.vectorByte = encryptedBlockMessage;
            for (int k = 0; k < this.lenBlock; k++) {
                this.encryptedMessageByte[k + i * this.lenBlock] = encryptedBlockMessage[k];
            }
        }
    }


    private byte[] encodeCBC(byte[] m) {
        byte[] res = new byte[m.length];
        for (int i = 0; i < m.length; i++) {
            res[i] = (byte) (m[i] ^ this.vectorByte[i]);
        }
        res = encryptionProcedureCBC(res);
        return res;
    }

    byte[] decoderCBC(String encriptedMessage, String vect) {
        encryptedMessageByte = encriptedMessage.getBytes();
        byte[] decryptedMessage = new byte[this.encryptedMessageByte.length];
        byte[] blockEncryptMessage = new byte[this.lenBlock];
        byte[] decryptedBlockMessage = new byte[this.lenBlock];
        for (int i = this.encryptedMessageByte.length / this.lenBlock - 1; i > 0; i--) {
            for (int j = 0; j < this.lenBlock; j++) {
                keyBlock[j] = this.keyByte[(j + i * this.lenBlock) % this.keyByte.length];
                blockEncryptMessage[j] = this.encryptedMessageByte[(j + i * this.lenBlock)];
                vectorByte[j] = this.encryptedMessageByte[(j + ((i - 1) * this.lenBlock))];
            }
            decryptedBlockMessage = encodeCBC(blockEncryptMessage);
            System.arraycopy(decryptedBlockMessage, 0, decryptedMessage, 0 + i * this.lenBlock, this.lenBlock);
        }
        byte[] vectorNaPoslShag = new byte[vectorByte.length];
        System.arraycopy(vectorByte, 0, vectorNaPoslShag, 0, vectorByte.length);
        vectorByte = vect.getBytes();
        for (int j = 0; j < this.keyBlock.length % keyByte.length; j++) {
            keyBlock[j] = this.keyByte[j];
        }
        decryptedBlockMessage = encodeCBC(vectorNaPoslShag);
        for (int k = 0; k < this.lenBlock; k++) {
            decryptedMessage[k] = decryptedBlockMessage[k];
        }
        return decryptedMessage;
    }

    public static String decodeCBC(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();
        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }
        return new String(res);
    }

    public static void main(String[] args) {
        char[][] d = {
                {0x05, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07}, {0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F}
        };
        char[][] x = {{0x75, 0xBB}, {0x9A, 0x4D}, {0x6B, 0xCB}, {0x45, 0x2A}, {0x71, 0x3A}, {0xDF, 0xB3}, {0x17, 0x90}, {0x51, 0x1F}};
        EncryptionModes.shiftRow(EncryptionModes.substitutionn(d));
    }

    public static char[][] substitutionn(char[][] masData) {
        char[][] ressult = new char[masData.length][masData[0].length];
        for (int i = 0; i < masData.length; i++) {
            for (int j = 0; j < masData[i].length; j++) {
                ressult[i][j] = Substitution.KEY[j % 4][masData[i][j]];
                System.out.print(Integer.toHexString(ressult[i][j]).toUpperCase() + " ");
                //75 BB 9A 4D 6B CB 45 2A 71 3A DF B3 17 90 51 1F
            }
        }
        System.out.println();
        return ressult;
    }

    public static char[][] shiftRow(char[][] masData) {
        char[][] ressult = new char[masData.length][masData[0].length];
        for (int i = 4; i < 8; i++) {
            char tmp = masData[0][i];
            masData[0][i] = masData[1][i];
            masData[1][i] = tmp;
                //ressult[i][j] = masData[i][(j + i) % masData[i].length];
                //System.out.print(Integer.toHexString(ressult[i][j]).toUpperCase() + " ");
        }
        for (int i = 0; i < masData.length; i++){
            for (int j = 0; j < masData[i].length; j++){
                System.out.print(Integer.toHexString(masData[i][j]).toUpperCase() + " ");
            }
        }
        System.out.println();

        return masData;
    } //75 BB 9A 4D 17 90 51 1F 71 3A DF B3 6B CB 45 2A
}
