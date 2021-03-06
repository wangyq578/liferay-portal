/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.sso.token.internal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.test.BaseCompanySettingsVerifyProcessTestCase;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Greenwald
 */
@RunWith(Arquillian.class)
public class ShibbolethCompanySettingsVerifyProcessTest
	extends BaseCompanySettingsVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected void doVerify(
		PortletPreferences portletPreferences, Settings settings) {

		String[] shibbolethKeys = {
			"shibboleth.auth.enabled", "shibboleth.import.from.ldap",
			"shibboleth.logout.url", "shibboleth.user.header"
		};

		for (String key : shibbolethKeys) {
			Assert.assertTrue(
				Validator.isNull(
					portletPreferences.getValue(key, StringPool.BLANK)));
		}

		Assert.assertTrue(
			GetterUtil.getBoolean(
				settings.getValue("enabled", StringPool.FALSE)));
		Assert.assertFalse(
			GetterUtil.getBoolean(
				settings.getValue("importFromLDAP", StringPool.TRUE)));
		Assert.assertEquals(
			"/test/shibboleth/url",
			settings.getValue("logoutRedirectURL", StringPool.BLANK));
		Assert.assertEquals(
			"testShibboleth",
			settings.getValue("userTokenName", StringPool.BLANK));
	}

	@Override
	protected String getSettingsId() {
		return "com.liferay.portal.security.sso.token";
	}

	@Override
	protected String getVerifyProcessName() {
		return "com.liferay.portal.security.sso.token.shibboleth";
	}

	@Override
	protected void populateLegacyProperties(UnicodeProperties properties) {
		properties.put("shibboleth.auth.enabled", "true");
		properties.put("shibboleth.import.from.ldap", "false");
		properties.put("shibboleth.logout.url", "/test/shibboleth/url");
		properties.put("shibboleth.user.header", "testShibboleth");
	}

}