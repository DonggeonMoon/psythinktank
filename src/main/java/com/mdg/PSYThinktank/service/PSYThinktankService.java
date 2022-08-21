package com.mdg.PSYThinktank.service;

import com.mdg.PSYThinktank.model.*;
import com.mdg.PSYThinktank.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PSYThinktankService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final StockInfoRepository stockInfoRepository;
    private final ShareRepository shareRepository;
    private final HRRRepository hrrRepository;
    private final DividendRepository dividendRepository;
    private final CircularRepository circularRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public boolean checkId(String memberId) {
        return (memberRepository.findById(memberId).isPresent());
    }

    @Transactional
    public boolean checkPw(String memberId, String memberPw) {
        return BCrypt.checkpw(memberPw, memberRepository.findById(memberId).orElse(new Member()).getMemberPw());
    }

    @Transactional
    public boolean checkEmail(String memberEmail) {
        return (memberRepository.findByMemberEmail(memberEmail) != null);
    }

    @Transactional
    public Map<String, Object> login(String memberId, String memberPw, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (!"".equals(memberId) && !"".equals(memberPw)) {
            if (checkId(memberId)) {
                Member member = memberRepository.findById(memberId).orElse(new Member());
                // 5회 이상 틀렸을 경우
                if (member.getLoginTryCount() < 5) {
                    if (checkPw(memberId, memberPw)) {
                        member.setLoginTryCount(0);
                        session.setAttribute("member", memberRepository.findById(memberId).orElse(new Member()));
                        map.put("isSucceeded", true);
                        map.put("error", -1);
                    } else {
                        member.setLoginTryCount(member.getLoginTryCount() + 1);
                        map.put("isSucceeded", false);
                        map.put("error", 3);
                    }
                    map.put("loginTryCount", member.getLoginTryCount());
                } else {
                    map.put("isSucceeded", false);
                    map.put("error", 4);
                }
                return map;
            } else
                map.put("isSucceeded", false);
            map.put("error", 2);
            return map;
        }
        map.put("isSucceeded", false);
        map.put("error", 1);
        return map;
    }

    @Transactional
    public void join(Member member) {
        member.setMemberPw(BCrypt.hashpw(member.getMemberPw(), BCrypt.gensalt()));
        memberRepository.save(member);
    }

    @Transactional
    public Member getMemberInfo(HttpSession session) {
        return memberRepository.findById(((Member) session.getAttribute("member")).getMemberId()).orElse(null);
    }

    @Transactional
    public void editMemberInfo(Member member) {
        Member newMember = memberRepository.findById(member.getMemberId()).orElse(new Member());
        newMember.setMemberPw(BCrypt.hashpw(member.getMemberPw(), BCrypt.gensalt()));
        newMember.setMemberEmail(member.getMemberEmail());
    }

    @Transactional
    public void changeUserLevel(Member member) {
        Member newMember = memberRepository.findById(member.getMemberId()).orElse(new Member());
        newMember.setUserLevel(member.getUserLevel());
    }

    @Transactional
    public void deleteMemberInfo(String memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public Page<Member> selectAllMember(int page) {
        return memberRepository.findAll(PageRequest.of(page, 50, Sort.by("userLevel").descending().and(Sort.by("memberId").ascending())));
    }

    @Transactional
    public Member selectOneMemberByEmail(String memberEmail) {
        return memberRepository.findByMemberEmail(memberEmail);
    }

    @Transactional
    public Member selectOneMemberByEmailAndId(String memberEmail, String memberId) {
        return memberRepository.findByMemberEmailAndMemberId(memberEmail, memberId);
    }

    @Transactional
    public Page<Board> selectAllBoard(int page) {
        return boardRepository.findAll(PageRequest.of(page, 10, Sort.by("isNotice").descending().and(Sort.by("boardNo").descending())));
    }

    @Transactional
    public Board selectOneBoard(int boardNo) {
        return boardRepository.findById(boardNo).orElse(null);
    }

    @Transactional
    public List<Board> searchBoardByBoardTitle(String boardTitle) {
        return boardRepository.findByBoardTitleContainingOrderByBoardNoDesc(boardTitle);
    }

    @Transactional
    public List<Board> searchBoardByBoardContent(String boardContent) {
        return boardRepository.findByBoardContentContainingOrderByBoardNoDesc(boardContent);
    }

    @Transactional
    public List<Board> searchBoardByMemberId(String memberId) {
        return boardRepository.findByMemberIdOrderByBoardNoDesc(memberId);
    }

    @Transactional
    public void insertOneBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void updateOneBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void deleteOneBoard(int boardNo) {
        boardRepository.deleteById(boardNo);
    }

    @Transactional
    public List<Comment> selectAllCommentByBoardNo(int boardNo) {
        return commentRepository.findAllByBoardNo(boardNo);
    }

    @Transactional
    public Comment selectOneComment(int commentNo) {
        return commentRepository.findById(commentNo).orElse(null);
    }

    @Transactional
    public void insertOneComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateOneComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteOneComment(int commentNo) {
        commentRepository.deleteById(commentNo);
    }

    @Transactional
    public void addHit(int board_no) {
        Board board = boardRepository.findById(board_no).orElse(new Board());
        board.setBoardHit(board.getBoardHit() + 1);
        boardRepository.save(board);
    }

    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(int comment_no) {
        commentRepository.deleteById(comment_no);
    }

    @Transactional
    public Page<StockInfo> selectAllStockInfo(int page) {
        return stockInfoRepository.findAll(PageRequest.of(page, 10, Sort.by("stockCode").ascending()));
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoByStockCode(String stockCode) {
        return stockInfoRepository.findByStockCodeLike("%" + stockCode + "%");
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoByStockName(String stockName) {
        return stockInfoRepository.findByStockNameLike("%" + stockName + "%");
    }

    @Transactional
    public List<Share> selectAllShareByStockCode(String stockCode) {
        return shareRepository.findByStockCode(stockCode);
    }

    @Transactional
    public StockInfo selectOneStockInfoByStockCode(String stockCode) {
        return stockInfoRepository.findById(stockCode).orElse(new StockInfo());
    }

    @Transactional
    public HRR selectOneHRRByStockCode(String stockCode) {
        return hrrRepository.findByStockCodeAndBsnsYearAndReprtCode(stockCode, "2021", "11011");
    }

    @Transactional
    public Dividend selectOneDividendByStockCode(String StockCode) {
        return dividendRepository.findById(StockCode).orElse(new Dividend());
    }

    @Transactional
    public void sendVerificationEmail(String email) {
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom("officialpsythinktank@gmail.com");
            helper.setTo(email);
            helper.setSubject("PSYThinktank 이메일 인증 메일입니다.");
            helper.setText("", true);
        };
        javaMailSender.send(preparator);
    }

    @Transactional
    public void sendTempPwEmail(String email) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            sb.append((char) (random.nextInt(57) + 'A'));
        }
        String randomizedStr = sb.toString();
        Member member = memberRepository.findByMemberEmail(email);
        member.setMemberPw(BCrypt.hashpw(randomizedStr, BCrypt.gensalt()));
        member.setLoginTryCount(0);
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom("officialpsythinktank@gmail.com");
            helper.setTo(email);
            helper.setSubject("임시 비밀번호를 보내드립니다.");
            helper.setText("임시비밀번호는 " + randomizedStr + "입니다.", true);
        };
        javaMailSender.send(preparator);
    }

    @Transactional
    public Page<Circular> selectAllCircular(int page) {
        return circularRepository.findAll(PageRequest.of(page, 10, Sort.by("circularId").ascending()));
    }

    @Transactional
    public Circular selectOneCircular(int circularId) {
        return circularRepository.findById(circularId).orElse(new Circular());
    }

    @Transactional
    @SuppressWarnings("SpellCheckingInspection")
    public void insertOneCircular(Circular circular, HttpServletRequest request, MultipartFile file) {
        String realPath = request.getServletContext().getRealPath("/uploadfile");
        String filePath = realPath + "/" + circular.getFileName();
        try {
            file.transferTo(new File(filePath));
            circularRepository.save(circular);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @SuppressWarnings("SpellCheckingInspection")
    public void deleteOneCircular(int circularId, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("/uploadfile");
        Circular circular = selectOneCircular(circularId);
        String filePath = realPath + "/" + circular.getFileName();
        try {
            if (new File(filePath).delete()) {
                circularRepository.deleteById(circularId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Transactional
    public Resource downloadCircular(int circularId, HttpServletRequest request) {
        try {
            String realPath = request.getServletContext().getRealPath("/uploadfile");
            Circular circular = selectOneCircular(circularId);
            Path filePath = Paths.get(realPath + "/" + circular.getFileName());
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("uri: " + filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
