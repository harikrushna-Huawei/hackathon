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

package org.onosproject.bgpio.types;

import com.google.common.base.MoreObjects;
import org.jboss.netty.buffer.ChannelBuffer;
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.util.Constants;
import org.onosproject.bgpio.util.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Provides implementation of extended community BGP Path Attribute.
 */
public class BgpCommunity implements BgpValueType {

    private static final Logger log = LoggerFactory.getLogger(BgpCommunity.class);
    public static final short TYPE = Constants.BGP_COMMUNITY;
    public static final byte FLAGS = (byte) 0xC0;
    public static final int  NO_EXPORT = 0xFFFFFF01;
    public static final int  NO_ADVERTISE = 0xFFFFFF02;
    public static final int  NO_EXPORT_SUBCONFED = 0xFFFFFF03;
    private Integer flag;

    /**
     * Constructor to initialize the value.
     *
     * @param flag BGP community flag
     */
    public BgpCommunity(int flag) {
        this.flag = flag;
    }

    /**
     * Constructor to initialize the value.
     *
     */
    public BgpCommunity() {
    }

    /**
     * Returns community value.
     *
     * @return community value
     */
    public Integer flag() {
        return this.flag;
    }

    /**
     * Reads from the channel buffer and parses extended community.
     *
     * @param cb ChannelBuffer
     * @return object of BgpExtendedCommunity
     * @throws BgpParseException while parsing extended community
     */
    public static BgpCommunity read(ChannelBuffer cb) throws BgpParseException {

        ChannelBuffer tempCb = cb.copy();
        Validation validation = Validation.parseAttributeHeader(cb);

        if (cb.readableBytes() < validation.getLength()) {
            Validation.validateLen(BgpErrorType.UPDATE_MESSAGE_ERROR, BgpErrorType.ATTRIBUTE_LENGTH_ERROR,
                    validation.getLength());
        }
        //if fourth bit is set, length is read as short otherwise as byte , len includes type, length and value
        int len = validation.isShort() ? validation.getLength() + Constants.TYPE_AND_LEN_AS_SHORT : validation
                .getLength() + Constants.TYPE_AND_LEN_AS_BYTE;
        ChannelBuffer data = tempCb.readBytes(len);
        if (validation.getFirstBit() && !validation.getSecondBit() && validation.getThirdBit()) {
            throw new BgpParseException(BgpErrorType.UPDATE_MESSAGE_ERROR, BgpErrorType.ATTRIBUTE_FLAGS_ERROR, data);
        }

        ChannelBuffer tempBuf = cb.readBytes(validation.getLength());
        if (tempBuf.readableBytes() == 4) {
            return new BgpCommunity(tempBuf.readInt());
        }
        return new BgpCommunity();
    }

    @Override
    public short getType() {
        return TYPE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BgpCommunity) {
            BgpCommunity other = (BgpCommunity) obj;
            return Objects.equals(flag, other.flag);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .omitNullValues()
                .add("flag", flag)
                .toString();
    }

    @Override
    public int write(ChannelBuffer cb) {
        int iLenStartIndex = cb.writerIndex();

        cb.writeByte(FLAGS);
        cb.writeByte(getType());

        cb.writeByte(4);
        cb.writeInt(flag());

        return cb.writerIndex() - iLenStartIndex;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }
}
