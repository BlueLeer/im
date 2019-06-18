/**
 * @author WangLe
 * @date 2019/6/18 15:05
 * @description
 */
public class Test {

    public static void main(String[] args) {
        String s = "85092859034578028508350284983750398503860938503759873953895r3698534";
        char c = statMostRateChar(s);
        System.out.println(c);
    }

    /**
     * 表驱动统计字符串中首次出现频率最高的数字
     * @param str
     * @return
     */
    public static char statMostRateChar(String str) {
        if (str != null && !"".equals(str)) {
            int charsStat[] = new int[128];
            int charsFirstIdx[] = new int[128];
            int strLen = str.length();

            for (int ch = 0; ch < 128; ch++) {
                charsFirstIdx[ch] = strLen;
            }

            // 統計字符出現的次數
            for (int idx = 0; idx < strLen; idx++) {
                charsStat[str.charAt(idx)]++;
                // 记录字符第一次出现的位置
                if (idx < charsFirstIdx[str.charAt(idx)]) {
                    charsFirstIdx[str.charAt(idx)] = idx;
                }
            }

            int mostRateChar = 0;
            for (int ch = 1; ch < 128; ch++) {
                if (charsStat[ch] == 0) {
                    continue;
                }
                // 找频率出现最高的字符
                if (charsStat[mostRateChar] < charsStat[ch]) {
                    mostRateChar = ch;
                    // 出现频率一样时，选择出现在前面的数
                } else if (charsStat[mostRateChar] == charsStat[ch] &&
                        charsFirstIdx[mostRateChar] > charsFirstIdx[ch]) {
                    mostRateChar = ch;
                }
            }

            return (char) mostRateChar;
        } else {
            return '\0';
        }
    }

}
