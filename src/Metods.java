public class Metods {
    public static void main(String[] args) {
        char[][] d1 = {
                {0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}
        };
        char[][] d2 = {
                {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07}, {0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F}
        };
        char[][] d3 = {
                {0x62, 0xC9, 0x7C, 0x6E, 0x6A, 0xBF, 0x41, 0x33}, {0xED, 0x51, 0x31, 0xD6, 0x24, 0xC7, 0xC1, 0x82}
        };
        LinearChanger.linearTransformation(Metods.shiftRow(Metods.substitutionn(Metods.addKeysMod_2_64(d1, d2))));
        Metods.xorRkey(d3, d2);
    }
    private static char[][] addKeysMod_2_64(char[][] mass1, char[][] mass2) {
        for (int i = 0; i < mass1.length; i++) {
            add_colsMod_2_64(mass1[i], mass2[i]);
        }
        for (char[] chars : mass1) {
            for (char aChar : chars) {
                System.out.print(Integer.toHexString(aChar).toUpperCase() + " ");
            }
        }
        System.out.println();
        return mass1;
    }
    private static char[][] xorRkey(char[][] mas1, char[][] mas2) {
        for (int i = 0; i < mas1.length; i++) {
            for (int j = 0; j < mas1[i].length; j++) {
                mas1[i][j] = (char) (mas1[i][j] ^ mas2[i][j]);
            }
        }
        for (char[] chars : mas1) {
            for (char aChar : chars) {
                System.out.print(Integer.toHexString(aChar).toUpperCase() + " ");
            }
        }
        System.out.println();
        return mas1;
    }
    private static void add_colsMod_2_64(char[] mas1, char[] mas2) {
        boolean flag = false;
        for (int i = 0; i < 8; i++) {
            if (flag) {
                mas1[i] += 1;
            }
            int result = mas1[i] + mas2[i];
            if (result > 255) {
                flag = true;
                result = result % 255;
            }
            mas1[i] = (char) result;
        }
    }
    private static char[][] substitutionn(char[][] masData) {
        char[][] ressult = new char[masData.length][masData[0].length];
        for (int i = 0; i < masData.length; i++) {
            for (int j = 0; j < masData[i].length; j++) {
                ressult[i][j] = Substitution.KEY[j % 4][masData[i][j]];
                System.out.print(Integer.toHexString(ressult[i][j]).toUpperCase() + " ");
            }
        }
        System.out.println();
        return ressult;
    }
    private static char[][] shiftRow(char[][] masData) {
        char[][] ressult = new char[masData.length][masData[0].length];
        for (int i = 4; i < 8; i++) {
            char tmp = masData[0][i];
            masData[0][i] = masData[1][i];
            masData[1][i] = tmp;
        }
        for (char[] aMasData : masData) {
            for (char anAMasData : aMasData) {
                System.out.print(Integer.toHexString(anAMasData).toUpperCase() + " ");
            }
        }
        System.out.println();
        return masData;
    }
}