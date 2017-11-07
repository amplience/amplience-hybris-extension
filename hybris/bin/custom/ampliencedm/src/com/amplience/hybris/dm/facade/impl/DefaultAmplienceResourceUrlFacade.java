/*
 * Copyright (c) 2016 Amplience
 */
package com.amplience.hybris.dm.facade.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;

import com.amplience.hybris.dm.data.AmplienceResourceData;
import com.amplience.hybris.dm.facade.AmplienceResourceUrlFacade;

import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.BusinessException;
import de.hybris.platform.site.BaseSiteService;

/**
 * Default implementation of AmplienceResourceUrlFacade
 */
public class DefaultAmplienceResourceUrlFacade implements AmplienceResourceUrlFacade
{
	private Map<String, String> typeToPath;
	private Set<String> securePageTypes;
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;
	private BaseSiteService baseSiteService;
	private UrlResolver<ProductModel> productModelUrlResolver;
	private UrlResolver<CategoryModel> categoryModelUrlResolver;
	private UrlResolver<ContentPageModel> contentPageModelUrlResolver;
	private ProductService productService;
	private CategoryService categoryService;
	private CMSPageService cmsPageService;

	protected CMSPageService getCmsPageService()
	{
		return cmsPageService;
	}

	@Required
	public void setCmsPageService(final CMSPageService cmsPageService)
	{
		this.cmsPageService = cmsPageService;
	}

	protected UrlResolver<ContentPageModel> getContentPageModelUrlResolver()
	{
		return contentPageModelUrlResolver;
	}

	@Required
	public void setContentPageModelUrlResolver(final UrlResolver<ContentPageModel> contentPageModelUrlResolver)
	{
		this.contentPageModelUrlResolver = contentPageModelUrlResolver;
	}

	protected UrlResolver<CategoryModel> getCategoryModelUrlResolver()
	{
		return categoryModelUrlResolver;
	}

	@Required
	public void setCategoryModelUrlResolver(final UrlResolver<CategoryModel> categoryModelUrlResolver)
	{
		this.categoryModelUrlResolver = categoryModelUrlResolver;
	}

	protected CategoryService getCategoryService()
	{
		return categoryService;
	}

	@Required
	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	protected ProductService getProductService()
	{
		return productService;
	}

	@Required
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	protected UrlResolver<ProductModel> getProductModelUrlResolver()
	{
		return productModelUrlResolver;
	}

	@Required
	public void setProductModelUrlResolver(final UrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

	protected Map<String, String> getTypeToPath()
	{
		return typeToPath;
	}

	@Required
	public void setTypeToPath(final Map<String, String> typeToPath)
	{
		this.typeToPath = typeToPath;
	}

	protected Set<String> getSecurePageTypes()
	{
		return securePageTypes;
	}

	@Required
	public void setSecurePageTypes(final Set<String> securePageTypes)
	{
		this.securePageTypes = securePageTypes;
	}

	protected SiteBaseUrlResolutionService getSiteBaseUrlResolutionService()
	{
		return siteBaseUrlResolutionService;
	}

	@Required
	public void setSiteBaseUrlResolutionService(final SiteBaseUrlResolutionService siteBaseUrlResolutionService)
	{
		this.siteBaseUrlResolutionService = siteBaseUrlResolutionService;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	@Required
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	// ------

	/**
	 * Get the Amplience resource data for a specific type of resource.
	 *
	 * @param requestUrl  The request Url
	 * @param type        The resource type - specified in the amplience ecommBride
	 * @param param       The optional parameter. Type specific.
	 * @return The Amplience resource data
	 */
	@Override
	public AmplienceResourceData getResourceUrl(final String requestUrl, final String type, final String param) throws BusinessException
	{
		final ResourceInfo resourceInfo = getResourceInfo(type, param);

		final String url = buildUrl(requestUrl, resourceInfo);
		final AmplienceResourceData resourceData = new AmplienceResourceData();
		resourceData.setUrl(url);
		return resourceData;
	}

	protected String buildUrl(final String requestUrl, final ResourceInfo resourceInfo)
	{
		final String path = resourceInfo.getPath();
		final String queryParams = resourceInfo.getQueryParams();

		if (queryParams != null && !queryParams.isEmpty())
		{
			return requestUrl + queryParams;
		}
		return requestUrl + path;
	}

	private ResourceInfo getResourceInfo(final String type, final String param) throws BusinessException
	{
		switch (type)
		{
			case "category":
				return getCategoryPageResourceInfo(param);
			case "product":
				return getProductPageResourceInfo(param);
			case "standalone-page":
				return getContentPageResourceInfo(param);
			case "search":
				return getSearchPageResourceInfo(param);
			default:
				return getDefaultResourceInfo(type);
		}
	}

	private ResourceInfo getDefaultResourceInfo(final String type) throws BusinessException {
		final String path = getTypeToPath().get(type);
		if (path == null)
		{
			throw new BusinessException("Unknown resource type [" + type + "]");
		}
		return new ResourceInfo(path, null, getSecurePageTypes().contains(type));
	}

	protected ResourceInfo getSearchPageResourceInfo(final String queryText)
	{
		return new ResourceInfo("/search", "text=" + queryText, getSecurePageTypes().contains("search"));
	}

	protected ResourceInfo getContentPageResourceInfo(final String pageUid) throws BusinessException
	{
		final ContentPageModel contentPageModel = getCmsPageService().getPageForLabelOrId(pageUid);
		final String url = getContentPageModelUrlResolver().resolve(contentPageModel);
		return new ResourceInfo(url, null, getSecurePageTypes().contains("standalone-page"));
	}

	protected ResourceInfo getProductPageResourceInfo(final String productCode)
	{
		final ProductModel productModel = getProductService().getProductForCode(productCode);
		final String url = getProductModelUrlResolver().resolve(productModel);
		return new ResourceInfo(url, null, getSecurePageTypes().contains("product"));
	}

	protected ResourceInfo getCategoryPageResourceInfo(final String categoryCode)
	{
		final CategoryModel categoryModel = getCategoryService().getCategoryForCode(categoryCode);
		final String url = getCategoryModelUrlResolver().resolve(categoryModel);
		return new ResourceInfo(url, null, getSecurePageTypes().contains("category"));
	}

	protected static class ResourceInfo
	{
		private final String path;
		private final String queryParams;
		private final boolean secure;

		protected ResourceInfo(final String path, final String queryParams, final boolean secure)
		{
			this.path = path;
			this.queryParams = queryParams;
			this.secure = secure;
		}

		public String getPath()
		{
			return path;
		}

		public String getQueryParams()
		{
			return queryParams;
		}

		public boolean isSecure()
		{
			return secure;
		}
	}
}
