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
package org.onosproject.flowapi;

import org.onlab.packet.IpAddress;

import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Extended traffic redirect implementation.
 */
public class DefaultExtTrafficRedirectToIp implements ExtTrafficActionRedirectToIp {

    private IpAddress ipAddress;
    private Short copy;
    private ExtFlowTypes.ExtType type;

    /**
     * Creates an object of type DefaultExtTrafficRedirect which contains traffic redirect action.
     *
     * @param ipAddress is a redirect IP
     * @param type ExtType type
     * @param copy copy
     */
    DefaultExtTrafficRedirectToIp(IpAddress ipAddress, Short copy, ExtFlowTypes.ExtType type) {
        this.ipAddress = ipAddress;
        this.copy = copy;
        this.type = type;
    }

    @Override
    public ExtType type() {
        return type;
    }

    @Override
    public IpAddress ipAddress() {
        return ipAddress;
    }

    @Override
    public Short copy() {
        return copy;
    }

    @Override
    public boolean exactMatch(ExtTrafficActionRedirectToIp value) {
        return this.equals(value) &&
                Objects.equals(this.ipAddress, value.ipAddress())
                && Objects.equals(this.type, value.type())
                && Objects.equals(this.copy, value.copy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, type, copy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DefaultExtTrafficRedirectToIp) {
            DefaultExtTrafficRedirectToIp that = (DefaultExtTrafficRedirectToIp) obj;
            return Objects.equals(ipAddress, that.ipAddress())
                    && Objects.equals(this.type, that.type())
                    && Objects.equals(this.copy, that.copy());
        }
        return false;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("ipAddress", ipAddress.toString())
                .add("type", type)
                .add("copy", copy)
                .toString();
    }

    /**
     * Builder class for extended redirect value rule.
     */
    public static class Builder implements ExtTrafficActionRedirectToIp.Builder {
        private IpAddress ipAddress;
        private Short copy;
        private ExtType type;

        @Override
        public Builder setIpAddress(IpAddress ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        @Override
        public Builder setCopy(Short copy) {
            this.copy = copy;
            return this;
        }

        @Override
        public Builder setType(ExtType type) {
            this.type = type;
            return this;
        }

        @Override
        public ExtTrafficActionRedirectToIp build() {
            checkNotNull(ipAddress, "ipAddress cannot be null");
            checkNotNull(copy, "copy cannot be null");
            return new DefaultExtTrafficRedirectToIp(ipAddress, copy, type);
        }
    }
}
