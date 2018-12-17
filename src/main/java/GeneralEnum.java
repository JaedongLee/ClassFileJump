/**
 * @author zoutairan
 * @since 2018/12/17
 */
public enum GeneralEnum {
    /**
     * output path
     */
    OUTPUT_PATH("outputPath"),
    ;

    private String value;

    GeneralEnum(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
