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
import org.onlab.packet.IpAddress;
import org.onosproject.bgpio.exceptions.BgpParseException;
import org.onosproject.bgpio.util.Constants;

import java.util.Objects;

/**
 * Provides implementation of BGP flow specification redirect to IP action.
 */
public class BgpFsActionReDirectToIp implements BgpValueType {

    public static final short TYPE = Constants.BGP_FLOWSPEC_ACTION_TRAFFIC_REDIRECT_TO_IP;
    private IpAddress ipAddress;
    private Short copy;

    /**
     * Constructor to initialize the value.
     *
     * @param ipAddress ip address
     * @param copy if set redirection applies to copies of the matching packets
     */
    public BgpFsActionReDirectToIp(IpAddress ipAddress, Short copy) {
        this.ipAddress = ipAddress;
        this.copy = copy;
    }

    @Override
    public short getType() {
        return this.TYPE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, copy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BgpFsActionReDirectToIp) {
            BgpFsActionReDirectToIp other = (BgpFsActionReDirectToIp) obj;
            return this.ipAddress.equals(other.ipAddress) && this.copy.equals(other.copy);
        }
        return false;
    }

    @Override
    public int write(ChannelBuffer cb) {
        int iLenStartIndex = cb.writerIndex();

        cb.writeShort(TYPE);

        cb.writeBytes(ipAddress.toOctets());
        cb.writeShort(copy);

        return cb.writerIndex() - iLenStartIndex;
    }

    /**
     * Reads the channel buffer and returns object.
     *
     * @param cb channelBuffer
     * @return object of flow spec action redirect
     * @throws BgpParseException while parsing BgpFsActionReDirectToIp
     */
    public static BgpFsActionReDirectToIp read(ChannelBuffer cb) throws BgpParseException {
        IpAddress ipAddress;
        Short copy;

        //TODO: check for length
        ipAddress = IpAddress.valueOf(cb.readInt());
        copy = cb.readShort();
        return new BgpFsActionReDirectToIp(ipAddress, copy);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("TYPE", TYPE)
                .add("ipAddress", ipAddress)
                .add("copy", copy).toString();
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }
}
