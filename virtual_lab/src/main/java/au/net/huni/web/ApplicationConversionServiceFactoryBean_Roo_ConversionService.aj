// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.web;

import au.net.huni.model.DataSource;
import au.net.huni.model.FeedbackItem;
import au.net.huni.model.HistoryItem;
import au.net.huni.model.Institution;
import au.net.huni.model.Project;
import au.net.huni.model.Registration;
import au.net.huni.model.ToolCategory;
import au.net.huni.model.ToolLibraryItem;
import au.net.huni.model.ToolParameter;
import au.net.huni.model.UserRole;
import au.net.huni.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Long, DataSource> ApplicationConversionServiceFactoryBean.getIdToDataSourceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.DataSource>() {
            public au.net.huni.model.DataSource convert(java.lang.Long id) {
                return DataSource.findDataSource(id);
            }
        };
    }
    
    public Converter<String, DataSource> ApplicationConversionServiceFactoryBean.getStringToDataSourceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.DataSource>() {
            public au.net.huni.model.DataSource convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), DataSource.class);
            }
        };
    }
    
    public Converter<FeedbackItem, String> ApplicationConversionServiceFactoryBean.getFeedbackItemToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.net.huni.model.FeedbackItem, java.lang.String>() {
            public String convert(FeedbackItem feedbackItem) {
                return new StringBuilder().append(feedbackItem.getContext()).append(' ').append(feedbackItem.getComment()).append(' ').append(feedbackItem.getFeedbackDate()).append(' ').append(feedbackItem.getVisitorIpAddress()).toString();
            }
        };
    }
    
    public Converter<Long, FeedbackItem> ApplicationConversionServiceFactoryBean.getIdToFeedbackItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.FeedbackItem>() {
            public au.net.huni.model.FeedbackItem convert(java.lang.Long id) {
                return FeedbackItem.findFeedbackItem(id);
            }
        };
    }
    
    public Converter<String, FeedbackItem> ApplicationConversionServiceFactoryBean.getStringToFeedbackItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.FeedbackItem>() {
            public au.net.huni.model.FeedbackItem convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), FeedbackItem.class);
            }
        };
    }
    
    public Converter<Long, HistoryItem> ApplicationConversionServiceFactoryBean.getIdToHistoryItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.HistoryItem>() {
            public au.net.huni.model.HistoryItem convert(java.lang.Long id) {
                return HistoryItem.findHistoryItem(id);
            }
        };
    }
    
    public Converter<String, HistoryItem> ApplicationConversionServiceFactoryBean.getStringToHistoryItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.HistoryItem>() {
            public au.net.huni.model.HistoryItem convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), HistoryItem.class);
            }
        };
    }
    
    public Converter<Long, Institution> ApplicationConversionServiceFactoryBean.getIdToInstitutionConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.Institution>() {
            public au.net.huni.model.Institution convert(java.lang.Long id) {
                return Institution.findInstitution(id);
            }
        };
    }
    
    public Converter<Long, Project> ApplicationConversionServiceFactoryBean.getIdToProjectConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.Project>() {
            public au.net.huni.model.Project convert(java.lang.Long id) {
                return Project.findProject(id);
            }
        };
    }
    
    public Converter<String, Project> ApplicationConversionServiceFactoryBean.getStringToProjectConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.Project>() {
            public au.net.huni.model.Project convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Project.class);
            }
        };
    }
    
    public Converter<Registration, String> ApplicationConversionServiceFactoryBean.getRegistrationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.net.huni.model.Registration, java.lang.String>() {
            public String convert(Registration registration) {
                return new StringBuilder().append(registration.getUserName()).append(' ').append(registration.getGivenName()).append(' ').append(registration.getFamilyName()).append(' ').append(registration.getEmailAddress()).toString();
            }
        };
    }
    
    public Converter<Long, Registration> ApplicationConversionServiceFactoryBean.getIdToRegistrationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.Registration>() {
            public au.net.huni.model.Registration convert(java.lang.Long id) {
                return Registration.findRegistration(id);
            }
        };
    }
    
    public Converter<String, Registration> ApplicationConversionServiceFactoryBean.getStringToRegistrationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.Registration>() {
            public au.net.huni.model.Registration convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Registration.class);
            }
        };
    }
    
    public Converter<ToolCategory, String> ApplicationConversionServiceFactoryBean.getToolCategoryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.net.huni.model.ToolCategory, java.lang.String>() {
            public String convert(ToolCategory toolCategory) {
                return new StringBuilder().append(toolCategory.getName()).toString();
            }
        };
    }
    
    public Converter<Long, ToolCategory> ApplicationConversionServiceFactoryBean.getIdToToolCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.ToolCategory>() {
            public au.net.huni.model.ToolCategory convert(java.lang.Long id) {
                return ToolCategory.findToolCategory(id);
            }
        };
    }
    
    public Converter<String, ToolCategory> ApplicationConversionServiceFactoryBean.getStringToToolCategoryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.ToolCategory>() {
            public au.net.huni.model.ToolCategory convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ToolCategory.class);
            }
        };
    }
    
    public Converter<Long, ToolLibraryItem> ApplicationConversionServiceFactoryBean.getIdToToolLibraryItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.ToolLibraryItem>() {
            public au.net.huni.model.ToolLibraryItem convert(java.lang.Long id) {
                return ToolLibraryItem.findToolLibraryItem(id);
            }
        };
    }
    
    public Converter<String, ToolLibraryItem> ApplicationConversionServiceFactoryBean.getStringToToolLibraryItemConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.ToolLibraryItem>() {
            public au.net.huni.model.ToolLibraryItem convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ToolLibraryItem.class);
            }
        };
    }
    
    public Converter<ToolParameter, String> ApplicationConversionServiceFactoryBean.getToolParameterToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.net.huni.model.ToolParameter, java.lang.String>() {
            public String convert(ToolParameter toolParameter) {
                return new StringBuilder().append(toolParameter.getName()).append(' ').append(toolParameter.getAmount()).append(' ').append(toolParameter.getDisplayOrder()).toString();
            }
        };
    }
    
    public Converter<Long, ToolParameter> ApplicationConversionServiceFactoryBean.getIdToToolParameterConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.ToolParameter>() {
            public au.net.huni.model.ToolParameter convert(java.lang.Long id) {
                return ToolParameter.findToolParameter(id);
            }
        };
    }
    
    public Converter<String, ToolParameter> ApplicationConversionServiceFactoryBean.getStringToToolParameterConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.ToolParameter>() {
            public au.net.huni.model.ToolParameter convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ToolParameter.class);
            }
        };
    }
    
    public Converter<UserRole, String> ApplicationConversionServiceFactoryBean.getUserRoleToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<au.net.huni.model.UserRole, java.lang.String>() {
            public String convert(UserRole userRole) {
                return new StringBuilder().append(userRole.getName()).toString();
            }
        };
    }
    
    public Converter<Long, UserRole> ApplicationConversionServiceFactoryBean.getIdToUserRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, au.net.huni.model.UserRole>() {
            public au.net.huni.model.UserRole convert(java.lang.Long id) {
                return UserRole.findUserRole(id);
            }
        };
    }
    
    public Converter<String, UserRole> ApplicationConversionServiceFactoryBean.getStringToUserRoleConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, au.net.huni.model.UserRole>() {
            public au.net.huni.model.UserRole convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), UserRole.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getDataSourceToStringConverter());
        registry.addConverter(getIdToDataSourceConverter());
        registry.addConverter(getStringToDataSourceConverter());
        registry.addConverter(getFeedbackItemToStringConverter());
        registry.addConverter(getIdToFeedbackItemConverter());
        registry.addConverter(getStringToFeedbackItemConverter());
        registry.addConverter(getHistoryItemToStringConverter());
        registry.addConverter(getIdToHistoryItemConverter());
        registry.addConverter(getStringToHistoryItemConverter());
        registry.addConverter(getInstitutionToStringConverter());
        registry.addConverter(getIdToInstitutionConverter());
        registry.addConverter(getStringToInstitutionConverter());
        registry.addConverter(getProjectToStringConverter());
        registry.addConverter(getIdToProjectConverter());
        registry.addConverter(getStringToProjectConverter());
        registry.addConverter(getRegistrationToStringConverter());
        registry.addConverter(getIdToRegistrationConverter());
        registry.addConverter(getStringToRegistrationConverter());
        registry.addConverter(getResearcherToStringConverter());
        registry.addConverter(getIdToResearcherConverter());
        registry.addConverter(getStringToResearcherConverter());
        registry.addConverter(getToolCategoryToStringConverter());
        registry.addConverter(getIdToToolCategoryConverter());
        registry.addConverter(getStringToToolCategoryConverter());
        registry.addConverter(getToolLibraryItemToStringConverter());
        registry.addConverter(getIdToToolLibraryItemConverter());
        registry.addConverter(getStringToToolLibraryItemConverter());
        registry.addConverter(getToolParameterToStringConverter());
        registry.addConverter(getIdToToolParameterConverter());
        registry.addConverter(getStringToToolParameterConverter());
        registry.addConverter(getUserRoleToStringConverter());
        registry.addConverter(getIdToUserRoleConverter());
        registry.addConverter(getStringToUserRoleConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
