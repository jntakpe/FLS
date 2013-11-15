package fr.sg.fls.generator;

import fr.sg.fls.generator.jaxb.SCTMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Générateur de messages SCT
 *
 * @author jntakpe
 */
public class SCTMessageGenerator {

    private int sequence = 0;

    public SCTWrapper generateMessage() {
        SCTMessage sctMessage;
        try {
            sctMessage = readConf();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<SCTMessage.Field> fields = sctMessage.getField();
        Map<String, String> fieldValue = new HashMap<String, String>(fields.size());
        StringBuilder builder = new StringBuilder(fields.size());
        Random random = new Random();
        for (SCTMessage.Field field : fields) {
            int length = field.getLength().intValue();
            if (!field.isMandatory() && random.nextBoolean()) {
                String value = genWhiteSpaces(length);
                fieldValue.put(field.getLabel(), value);
                builder.append(value);
            } else {
                String valuesStr = field.getValues();
                if (StringUtils.isNotBlank(valuesStr)) {
                    String value = pickRdmValue(valuesStr, length, random, field.getPad());
                    fieldValue.put(field.getLabel(), value);
                    builder.append(value);
                } else {
                    String value = generateRdm(length, field.getPad(), field.getType(), random.nextInt(length));
                    fieldValue.put(field.getLabel(), value);
                    builder.append(value);
                }
            }
        }
        return new SCTWrapper(builder.toString(), fieldValue);
    }

    private SCTMessage readConf() throws Exception {
        Unmarshaller unmarshaller = JAXBContext.newInstance("fr.sg.fls.generator.jaxb").createUnmarshaller();
        return (SCTMessage) unmarshaller.unmarshal(this.getClass().getResourceAsStream("/SCTMessage.xml"));
    }

    private String genWhiteSpaces(int length) {
        return StringUtils.repeat(" ", length);
    }

    private String pickRdmValue(String valuesStr, int length, Random random, String pad) {
        String[] values = valuesStr.split(",");
        String value = values[random.nextInt(values.length)];
        if (value.length() == length) return value;
        else {
            if (StringUtils.isBlank(pad) || "right".equalsIgnoreCase(pad)) return StringUtils.rightPad(value, length);
            else return StringUtils.leftPad(value, length);
        }
    }

    private String generateRdm(int length, String pad, String type, int rdmLength) {
        if (type.equalsIgnoreCase("string"))
            return StringUtils.rightPad(RandomStringUtils.randomAlphabetic(rdmLength), length);
        else if (type.equalsIgnoreCase("numeric"))
            return StringUtils.rightPad(RandomStringUtils.randomNumeric(rdmLength), length);
        else return StringUtils.rightPad(String.valueOf(sequence++), length);
    }


}
