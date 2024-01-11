package com.catpaw.catpawmiddleware.service.portfolio;

import com.catpaw.catpawcore.domain.dto.repository.PortFolioDto;
import com.catpaw.catpawcore.domain.dto.service.file.FileTarget;
import com.catpaw.catpawcore.domain.entity.FileMaster;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.eumns.TargetType;
import com.catpaw.catpawcore.exception.custom.DataNotFoundException;
import com.catpaw.catpawcore.exception.custom.MemberNotFoundException;
import com.catpaw.catpawcore.service.file.FileService;
import com.catpaw.catpawcore.service.member.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortfolioService {

    private final FileService fileService;
    private final MemberService memberService;

    public PortfolioService(FileService fileService, MemberService memberService) {
        this.fileService = fileService;
        this.memberService = memberService;
    }

    public List<PortFolioDto> getPortfolioList(long memberId) {
        return fileService.getFileSummaryList(new FileTarget(memberId, TargetType.PORTFOLIO)).stream()
                .map(summary ->
                        new PortFolioDto(memberId, summary.fileId(), summary.absoluteDestination(), summary.fileOriginalName(), summary.created(), summary.updated()))
                .toList();
    }

    public PortFolioDto getMainPortfolio(long memberId) {
        PortFolioDto mainPortFolio = fileService.getMainPortfolio(memberId);

        if (mainPortFolio.getMemberId() == null) {
            throw new MemberNotFoundException("존재하지 않는 회원입니다.");
        }

        if (mainPortFolio.getFileId() == null) {
            throw new DataNotFoundException("메인 포트폴리오가 존재하지 않습니다.");
        }

        return mainPortFolio;
    }

    @Transactional
    public void modifyMainPortfolio(long memberId, long fileId) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> { throw new MemberNotFoundException(); });

        FileMaster fileMaster = fileService.findById(fileId)
                .orElseThrow(() -> { throw new DataNotFoundException(); });

        member.changeMainPortfolioId(fileMaster.getId());
    }
}
