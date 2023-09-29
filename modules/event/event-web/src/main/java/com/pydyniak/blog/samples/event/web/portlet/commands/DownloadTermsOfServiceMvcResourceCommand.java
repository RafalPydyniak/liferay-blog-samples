package com.pydyniak.blog.samples.event.web.portlet.commands;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.pydyniak.blog.samples.event.constants.EventWebPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

@Component(property = {
        "javax.portlet.name=" + EventWebPortletKeys.EVENTWEB,
        "mvc.command.name=" + EventWebPortletKeys.MVC_COMMAND_DOWNLOAD_TERMS_OF_SERVICE
}, service = MVCResourceCommand.class
)
public class DownloadTermsOfServiceMvcResourceCommand implements MVCResourceCommand {
    private static final Log LOG = LogFactoryUtil.getLog(DownloadTermsOfServiceMvcResourceCommand.class);
    private static final String TERMS_OF_SERVICE_FILE_TITLE = "events-terms-of-service";
    @Reference
    private DLFileEntryService dlFileEntryService;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)  {
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
        try {
            //For testing reasons we just assume the file name & that it is in the root directory of documents & media
            DLFileEntry fileEntry = dlFileEntryService.getFileEntry(themeDisplay.getScopeGroupId(),
                    DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, TERMS_OF_SERVICE_FILE_TITLE);
            PortletResponseUtil.sendFile(resourceRequest, resourceResponse, fileEntry.getFileName(), fileEntry.getContentStream(),
                    ContentTypes.APPLICATION_PDF);
            return false;
        } catch (Exception ex) {
            LOG.error("Error while fetching terms of service file or sending the response", ex);
            //SEssion errors don't work properly; TODO fix it - see the view - it redirects to blank page
            SessionErrors.add(resourceRequest, "terms-of-service-download-error");
            return true;
        }
    }
}
