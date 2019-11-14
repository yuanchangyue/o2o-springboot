package com.changyue.o2o.service;

import com.changyue.o2o.dto.AwardExecution;
import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.entity.Award;

import java.io.IOException;


public interface AwardService {

	/**
	 *
	 * @param awardCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	AwardExecution getAwardList(Award awardCondition, int pageIndex,
								int pageSize);

	/**
	 * 
	 * @param awardId
	 * @return
	 */
	Award getAwardById(long awardId);

	/**
	 * 
	 * @param award
	 * @param thumbnail
	 * @return
	 */
	AwardExecution addAward(Award award, ImageHolder thumbnail) throws IOException;

	/**
	 * 
	 * @param award
	 * @param thumbnail
	 * @return
	 */
	AwardExecution modifyAward(Award award, ImageHolder thumbnail) throws IOException;

}
