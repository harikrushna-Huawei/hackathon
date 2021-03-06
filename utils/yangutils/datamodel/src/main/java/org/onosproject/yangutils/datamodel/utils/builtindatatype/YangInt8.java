/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.yangutils.datamodel.utils.builtindatatype;

import java.io.Serializable;

import org.onosproject.yangutils.datamodel.YangDataTypes;

/**
 * Handles the YANG's int8 data type processing.
 *
 * int8 represents integer values between -128 and 127, inclusively.
 */
public class YangInt8 implements YangBuiltInDataTypeInfo<YangInt8>, Serializable {

    private static final long serialVersionUID = 8006201664L;

    /**
     * YANG's min keyword.
     */
    private static final String MIN_KEYWORD = "min";

    /**
     * YANG's max keyword.
     */
    private static final String MAX_KEYWORD = "max";

    /**
     * Valid minimum value of YANG's int8.
     */
    public static final byte MIN_VALUE = -128;

    /**
     * Valid maximum value of YANG's int8.
     */
    public static final byte MAX_VALUE = 127;

    /**
     * The value of YANG's int8.
     */
    private final byte value;

    /**
     * Creates an object with the value initialized with value represented in
     * string.
     *
     * @param valueInString value of the object in string
     */
    public YangInt8(String valueInString) {

        if (valueInString.matches(MIN_KEYWORD)) {
            value = MIN_VALUE;
        } else if (valueInString.matches(MAX_KEYWORD)) {
            value = MAX_VALUE;
        } else {
            try {
                value = Byte.parseByte(valueInString);
            } catch (Exception e) {
                throw new DataTypeException("YANG file error : Input value \"" + valueInString + "\" is not a valid " +
                        "int8.");
            }
        }
    }

    /**
     * Returns YANG's int8 value.
     *
     * @return value of YANG's int8
     */
    public byte getValue() {
        return value;
    }

    @Override
    public int compareTo(YangInt8 anotherYangInt8) {
        return Byte.compare(value, anotherYangInt8.value);
    }

    @Override
    public YangDataTypes getYangType() {
        return YangDataTypes.INT8;
    }

}
