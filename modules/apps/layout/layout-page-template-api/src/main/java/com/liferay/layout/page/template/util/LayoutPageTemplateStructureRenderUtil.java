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

package com.liferay.layout.page.template.util;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureRenderUtil {

	public static String renderLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			LayoutPageTemplateStructure layoutPageTemplateStructure)
		throws PortalException {

		return renderLayoutContent(
			request, response, layoutPageTemplateStructure,
			FragmentEntryLinkConstants.VIEW, null);
	}

	public static String renderLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			String mode, Map<String, Object> parameterMap)
		throws PortalException {

		String data = layoutPageTemplateStructure.getData();

		if (Validator.isNull(data)) {
			return StringPool.BLANK;
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutPageTemplateStructure.getData());

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			sb.append("<div class=\"row\">");

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				sb.append("<div class=\"col col-");

				String size = GetterUtil.getString(
					columnJSONObject.getString("size"));

				sb.append(size);

				sb.append("\">");

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length();
					 k++) {

					long fragmentEntryLinkId =
						fragmentEntryLinkIdsJSONArray.getLong(k);

					if (fragmentEntryLinkId <= 0) {
						continue;
					}

					FragmentEntryLink fragmentEntryLink =
						FragmentEntryLinkLocalServiceUtil.
							fetchFragmentEntryLink(fragmentEntryLinkId);

					if (fragmentEntryLink == null) {
						continue;
					}

					String renderFragmentEntryLink = StringPool.BLANK;

					if (parameterMap != null) {
						renderFragmentEntryLink =
							FragmentEntryRenderUtil.renderFragmentEntryLink(
								fragmentEntryLink, mode, parameterMap, request,
								response);
					}
					else {
						renderFragmentEntryLink =
							FragmentEntryRenderUtil.renderFragmentEntryLink(
								fragmentEntryLink, mode, request, response);
					}

					sb.append(renderFragmentEntryLink);
				}

				sb.append("</div>");
			}

			sb.append("</div>");
		}

		return sb.toString();
	}

}