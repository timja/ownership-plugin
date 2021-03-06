/*
 * The MIT License
 *
 * Copyright 2013 Oleg Nenashev <nenashev@synopsys.com>, Synopsys Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.synopsys.arc.jenkins.plugins.ownership.util;

import com.synopsys.arc.jenkins.plugins.ownership.OwnershipDescription;
import javax.annotation.Nonnull;

/**
 * Provides handlers for ownership description.
 * @author Oleg Nenashev <nenashev@synopsys.com>, Synopsys Inc.
 * @since 0.4
 * @see OwnershipDescription
 */
public class OwnershipDescriptionHelper {
    
    private OwnershipDescriptionHelper() {}
    
    /**
     * Gets id of the owner.
     * @param descr Ownership description 
     * @return userId of the primary owner. The result will be "unknown" if the
     * user is not specified.
     */
    public static @Nonnull String getOwnerID(@Nonnull OwnershipDescription descr) {
        return descr.getPrimaryOwnerId();
    }
    
    /**
     * Gets owner's e-mail.
     * @param descr Ownership description
     * @return Owner's e-mail or empty string if it is not available 
     */
    public static String getOwnerEmail(@Nonnull OwnershipDescription descr) {
        String ownerEmail = UserStringFormatter.formatEmail(descr.getPrimaryOwnerId());
        return ownerEmail != null ? ownerEmail : "";
    }
    
    /**
     * Gets a comma-separated list of co-owners.
     * @param descr Ownership description
     * @return List of co-owner user IDs
     */
    public static @Nonnull String getCoOwnerIDs(@Nonnull OwnershipDescription descr) {
        StringBuilder coowners= new StringBuilder(getOwnerID(descr));
        for (String userId : descr.getCoownersIds()) {
            if (coowners.length() == 0) {
                coowners.append(",");
            }
            coowners.append(userId);      
        }
        return coowners.toString();
    }
    
    /**
     * Gets e-mails of co-owners.
     * @param descr Ownership description
     * @return Comma-separated list of co-owner e-mails (may be empty)
     */
    public static String getCoOwnerEmails(@Nonnull OwnershipDescription descr) {
        StringBuilder coownerEmails=new StringBuilder(getOwnerEmail(descr));
        for (String userId : descr.getCoownersIds()) {          
            String coownerEmail = UserStringFormatter.formatEmail(userId);
            if (coownerEmail != null) {
                if (coownerEmails.length() != 0) {
                    coownerEmails.append(",");
                }
                coownerEmails.append(coownerEmail);
            }       
        }
        return coownerEmails.toString();
    }
}
