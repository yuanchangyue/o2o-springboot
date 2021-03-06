package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.AwardDao;
import com.changyue.o2o.dto.AwardExecution;
import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.emums.AwardStateEnum;
import com.changyue.o2o.entity.Award;
import com.changyue.o2o.service.AwardService;
import com.changyue.o2o.util.ImageUtil;
import com.changyue.o2o.util.PathUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {

	@Autowired
	private AwardDao awardDao;

	@Override
	public AwardExecution getAwardList(Award awardCondition, int pageIndex,
									   int pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Award> awardList = awardDao.queryAwardAllList(awardCondition);
		PageInfo<Award> awardPageInfo = new PageInfo<>(awardList, 5);
		AwardExecution ae = new AwardExecution();
		ae.setAwardPageInfo(awardPageInfo);
		return ae;
	}

	@Override
	public Award getAwardById(long awardId) {
		return awardDao.queryAwardById(awardId);
	}

	@Override
	@Transactional
	public AwardExecution addAward(Award award, ImageHolder thumbnail) throws IOException {
		if (award != null && award.getShopId() != null) {
			award.setCreateTime(new Date());
			award.setLastEditTime(new Date());
			award.setEnableStatus(1);
			if (thumbnail != null) {
				addThumbnail(award, thumbnail);
			}
			try {
				int effectedNum = awardDao.insertAward(award);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品失败:" + e.toString());
			}
			return new AwardExecution(AwardStateEnum.SUCCESS, award);
		} else {
			return new AwardExecution(AwardStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public AwardExecution modifyAward(Award award, ImageHolder thumbnail) throws IOException {
		if (award != null && award.getShopId() != null) {
			award.setLastEditTime(new Date());
			if (thumbnail != null) {
				Award tempAward = awardDao.queryAwardById(award
						.getAwardId());
				if (tempAward.getAwardImg() != null) {
					ImageUtil.deleteImgFileOrPath(tempAward.getAwardImg());
				}
				addThumbnail(award, thumbnail);
			}
			try {
				int effectedNum = awardDao.updateAward(award);
				if (effectedNum <= 0) {
					throw new RuntimeException("更新商品信息失败");
				}
				return new AwardExecution(AwardStateEnum.SUCCESS, award);
			} catch (Exception e) {
				throw new RuntimeException("更新商品信息失败:" + e.toString());
			}
		} else {
			return new AwardExecution(AwardStateEnum.EMPTY);
		}
	}

	private void addThumbnail(Award award, ImageHolder thumbnail) throws IOException {
		String dest = PathUtils.getShopImagePath(award.getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnails(thumbnail, dest);
		award.setAwardImg(thumbnailAddr);
	}

}
