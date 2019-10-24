package cloud.tianai.order.core.util.image;

import lombok.Data;
import lombok.Getter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @Auther: 天爱有情
 * @Date: 2019/9/3 12:22
 * @Description: 用于快速校验该图片是什么类型的 以及该图片的宽高
 */
public class ImageDecoder {

    private static final int HEADER_INFO_LENGTH = 8;

    @Getter
    public enum ImageType {
        GIF,
        JPG,
        PNG,
        BMP,
        UNKNOWN
    }

    @Data
    public class ImageInfo {
        private ImageType imageType;
        private Integer width;
        private Integer height;
    }

    public static ImageInfo readUrl(URL url) throws IOException {
        return read(url.openStream());
    }

    public static ImageInfo readUrl(String url) throws IOException {
        return readUrl(new URL(url));
    }

    public static ImageInfo read(InputStream is) throws IOException {
        final ImageDecoder decoder = new ImageDecoder();
        ImageInfo imageInfo = null;

        if (null != is) {
            is = new BufferedInputStream(is, 8);
            is.mark(8);
            byte[] headerData = new byte[HEADER_INFO_LENGTH];
            int length = is.read(headerData);
            if (length == 8) {

                ImageType imageType = readHeader(headerData);

                imageInfo = decoder.new ImageInfo();
                imageInfo.setImageType(imageType);
                readSize(imageInfo, is, headerData);

                is.close();
            }
        }

        return imageInfo;
    }

    private static ImageType readHeader(byte[] headerData) {
        ImageType imageType = ImageType.UNKNOWN;
        if (null != headerData && headerData.length == HEADER_INFO_LENGTH) {
            if (matchPNG(headerData)) {
                imageType = ImageType.PNG;
            } else if (matchJPG(headerData)) {
                imageType = ImageType.JPG;
            } else if (matchBMP(headerData)) {
                imageType = ImageType.BMP;
            } else if (matchGIF(headerData)) {
                imageType = ImageType.GIF;
            }
        }
        return imageType;
    }

    private static void readSize(ImageInfo imageInfo, InputStream is, byte[] headerData) throws IOException {
        if (null != imageInfo && ImageType.UNKNOWN != imageInfo.getImageType() && null != is && null != headerData && headerData.length == HEADER_INFO_LENGTH) {

            Integer width = null;
            Integer height = null;

            switch (imageInfo.getImageType()) {
                case PNG: {
                    is.skip(16 - HEADER_INFO_LENGTH);//16
                    width = readInt(is, 4, true);
                    height = readInt(is, 4, true);
                }
                break;
                case JPG: {
                    is.reset();
                    skipBytesFromStream(is, 2);
                    while (0xFF == is.read()) {
                        int marker = is.read();
                        int len = readInt(is, 2, true);

                        if (marker >= 0xC0 && marker <= 0xC3) {
                            skipBytesFromStream(is, 1);
                            height = readInt(is, 2, true);
                            width = readInt(is, 2, true);
                            break;
                        }
                        skipBytesFromStream(is, len - 2);
                    }

                }
                break;
                case BMP: {
                    is.skip(18 - HEADER_INFO_LENGTH);
                    width = readInt(is, 4, false);
                    height = readInt(is, 4, false);

                }
                break;
                case GIF: {

                    byte[] widthByteArr = new byte[2];
                    widthByteArr[0] = headerData[6];
                    widthByteArr[1] = headerData[7];

                    byte[] heightByteArr = new byte[2];
                    int readLength = is.read(heightByteArr);

                    if (readLength == 2) {
                        width = readInt(widthByteArr, 2, false);
                        height = readInt(heightByteArr, 2, false);
                    }

                }
                break;
                default: {
                }
                break;
            }

            if (null != width) {
                imageInfo.setWidth(width);
            }
            if (null != height) {
                imageInfo.setHeight(height);
            }
            imageInfo.setHeight(height);
        }
    }

    /*------------------------------------*/
    //
    //
    //              util
    //
    //
    /*------------------------------------*/

    private static boolean matchGIF(byte[] headerData) {
        byte[] markBuf1 = "GIF89a".getBytes();
        byte[] markBuf2 = "GIF87a".getBytes();

        return compare(headerData, markBuf1) || compare(headerData, markBuf2);
    }

    private static boolean matchJPG(byte[] headerData) {
        byte[] markBuf = {(byte) 0xFF, (byte) 0xD8};
        return compare(headerData, markBuf);
    }

    private static boolean matchPNG(byte[] headerData) {
        byte[] markBuf = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        return compare(headerData, markBuf);
    }

    private static boolean matchBMP(byte[] headerData) {
        byte[] markBuf = {(byte) 0x42, (byte) 0x4D};
        return compare(headerData, markBuf);
    }

    private static boolean compare(byte[] arr, byte[] otherArr) {
        boolean isCompare = true;
        if (null != arr && null != otherArr && arr.length >= otherArr.length) {
            for (int i = 0; i < otherArr.length; i++) {
                if (arr[i] != otherArr[i]) {
                    isCompare = false;
                    break;
                }
            }
        } else {
            isCompare = false;
        }
        return isCompare;
    }

    /**
     * 通过byte计算int 分低位高位前后 bigEndian/littleEndian
     *
     * @param is
     * @param noOfBytes
     * @param bigEndian
     * @return
     * @throws IOException
     */
    private static int readInt(InputStream is, int noOfBytes, boolean bigEndian) throws IOException {
        int ret = 0;
        byte[] data = new byte[noOfBytes];
        if (is.read(data) == noOfBytes) {
            ret = readInt(data, noOfBytes, bigEndian);
        }
        return ret;
    }

    private static int readInt(byte[] data, int noOfBytes, boolean bigEndian) {
        int ret = 0;
        int sv = bigEndian ? ((noOfBytes - 1) * 8) : 0;
        int cnt = bigEndian ? -8 : 8;
        for (int i = 0; i < noOfBytes; i++) {
            ret |= bigEndian ? (data[i] & 0xFF) << sv : (data[i] << sv) & (0xFF << (sv / 4));
            sv += cnt;
        }
        return ret;
    }

//    private static int readIntII(InputStream is, int noOfBytes, boolean bigEndian) throws IOException {
//        int ret = 0;
//        int sv = bigEndian ? ((noOfBytes - 1) * 8) : 0;
//        int cnt = bigEndian ? -8 : 8;
//        for(int i=0;i<noOfBytes;i++) {
//            ret |= is.read() << sv;
//            sv += cnt;
//        }
//        return ret;
//    }

    private static long skipBytesFromStream(InputStream inputStream, long n) {
        long remaining = n;
        // SKIP_BUFFER_SIZE is used to determine the size of skipBuffer
        int SKIP_BUFFER_SIZE = 2048;
        // skipBuffer is initialized in skip(long), if needed.
        byte[] skipBuffer = new byte[SKIP_BUFFER_SIZE];
        int nr = 0;
        byte[] localSkipBuffer = skipBuffer;
        if (n <= 0) {
            return 0;
        }
        while (remaining > 0) {
            try {
                nr = inputStream.read(localSkipBuffer, 0,
                        (int) Math.min(SKIP_BUFFER_SIZE, remaining));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }
        return n - remaining;
    }

}