package com.thoughtmechanix.assets.controllers;

import com.thoughtmechanix.assets.model.Asset;
import com.thoughtmechanix.assets.services.AssetService;
import com.thoughtmechanix.assets.config.ServiceConfig;
import com.thoughtmechanix.assets.exception.ResourceNotFoundException;
import com.thoughtmechanix.assets.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping(value="v1/organizations/{organizationId}/assets")
public class AssetServiceController {
	private static final Logger logger = LoggerFactory.getLogger(AssetServiceController.class);
	@Autowired
	private AssetService assetService;

	@Autowired
	private ServiceConfig serviceConfig;

	/**
	 * Get all assets for the organization
	 * Using @PathVariable organizationId
	 */
	@RequestMapping(value="/",method = RequestMethod.GET)
	public List<Asset> getAssets( @PathVariable("organizationId") String organizationId) {
		logger.debug("AssetServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		return assetService.getAssetsByOrg(organizationId);
	}

	/**
	 * Get asset by asset id for organization by organization id
	 * Using @PathVariable organizationId
	 * Using @PathVariable assetId
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value="/{assetId}",method = RequestMethod.GET)
	public Asset getAssets( @PathVariable("organizationId") String organizationId,
			@PathVariable("assetId") String assetId) {
		logger.info("Getting asset with organizationId: {} and assetId:{}", organizationId, assetId);
		return assetService.getAsset(organizationId, assetId);
	}

	/**
	 * POST asset for organization by organization id
	 * Using @PathVariable organizationId
	 * Using @RequestBody asset
	 */
	@RequestMapping(value="/",method = RequestMethod.POST)
	public void saveAssets(@PathVariable("organizationId") String organizationId, 
			@RequestBody Asset asset) {
		logger.info("Adding asset: {} with organizationId: {}", asset, organizationId);
		assetService.saveAsset(organizationId, asset);
	}

	/**
	 * PUT asset by asset id for organization by organization id
	 * Using @PathVariable organizationId
	 * Using @PathVariable assetId
	 * Using @RequestBody asset
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="{assetId}",method = RequestMethod.PUT)
	public void updateAssets(@PathVariable("organizationId") String organizationId,
			@PathVariable("assetId") String assetId, @RequestBody Asset asset) throws ResourceNotFoundException {
		logger.info("Updating asset: {} with organizationId: {} and assetId: {}", asset, organizationId, assetId);
		assetService.updateAsset(organizationId, assetId, asset);
	}

	/**
	 * DELETE asset by organization id and asset id
	 * Using @PathVariable organizationId
	 * Using @PathVariable assetId
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="{assetId}",method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteAssets(@PathVariable("organizationId") String organizationId,
			@PathVariable("assetId") String assetId) throws ResourceNotFoundException {
		logger.info("Deleting asset with organizationId: {} and assetId: {}", organizationId, assetId);
		assetService.deleteAsset(organizationId, assetId);
		return HttpStatus.OK.toString();
	}

}
