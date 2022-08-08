package com.mdg.PSYThinktank.service;

import com.mdg.PSYThinktank.model.*;
import com.mdg.PSYThinktank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.mail.internet.MimeMessage;
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
public class PSYThinktankService {
	@Autowired
	BoardRepository bdao;

	@Autowired
	CommentRepository cdao;

	@Autowired
	MemberRepository mdao;

	@Autowired
	StockInfoRepository sidao;

	@Autowired
	ShareRepository sdao;

	@Autowired
	HRRRepository hdao;
	
	@Autowired
	DividendRepository ddao;
	
	@Autowired
	CircularRepository crdao;
	
	@Autowired
	JavaMailSender mailSender;

	@Transactional
	public boolean checkId(String memberId) {
		return ((mdao.findById(memberId).isPresent()) ? true : false);
	}

	@Transactional
	public boolean checkPw(String memberId, String memberPw) {
		return (BCrypt.checkpw(memberPw, mdao.findById(memberId).orElse(null).getMemberPw())) ? true : false;
	}
	
	@Transactional
	public boolean checkEmail(String memberEmail) {
		return ((mdao.findByMemberEmail(memberEmail) != null) ? true : false);
	}

	@Transactional
	public Map<String, Object> login(String memberId, String memberPw, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (memberId != "" && memberPw != "") {
			if (checkId(memberId) == true) {
				Member member = mdao.findById(memberId).get();
				if (member.getLoginTryCount() < 5) {
					if (checkPw(memberId, memberPw)) {
						member.setLoginTryCount(0);
						session.setAttribute("member", mdao.findById(memberId).get());
						map.put("isSucceeded", true);
						map.put("error", -1);
						map.put("loginTryCount", member.getLoginTryCount());
						return map;
					} else {
						member.setLoginTryCount(member.getLoginTryCount() + 1);
						map.put("isSucceeded", false);
						map.put("error", 3);
						map.put("loginTryCount", member.getLoginTryCount());
						return map;
					}					
				} else {
					map.put("isSucceeded", false);
					map.put("error", 4);
					return map;// 5회 이상 틀렸을 경우
				}
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
	public int getLoginTryCount(String memberId) {
		return mdao.findById(memberId).get().getLoginTryCount();
	}

	@Transactional
	public Boolean checkLogin(HttpSession session) {
		return (session.getAttribute("member") != null) ? true : false;
	}

	@Transactional
	public void join(Member member) {
		member.setMemberPw(BCrypt.hashpw(member.getMemberPw(), BCrypt.gensalt()));
		mdao.save(member);
	}

	@Transactional
	public Member getMemberInfo(HttpSession session) {
		return mdao.findById(((Member) session.getAttribute("member")).getMemberId()).orElse(null);
	}

	@Transactional
	public void editMemberInfo(Member member) {
		Member newMember = mdao.findById(member.getMemberId()).get();
		newMember.setMemberPw(BCrypt.hashpw(member.getMemberPw(), BCrypt.gensalt()));
		newMember.setMemberEmail(member.getMemberEmail());
	}

	@Transactional
	public void changeUserLevel(Member member) {
		Member newMember = mdao.findById(member.getMemberId()).get();
		newMember.setUserLevel(member.getUserLevel());
	}

	@Transactional
	public void deleteMemberInfo(String memberId) {
		mdao.deleteById(memberId);
	}

	@Transactional
	public Page<Member> selectAllMember(int page) {
		return mdao.findAll(PageRequest.of(page, 50, Sort.by("userLevel").descending().and(Sort.by("memberId").ascending())));
	}
	
	@Transactional
	public Member selectOneMemberByEmail(String memberEmail) {
		return mdao.findByMemberEmail(memberEmail);
	}
	
	@Transactional
	public Member selectOneMemberByEmailAndId(String memberEmail, String memberId) {
		return mdao.findByMemberEmailAndMemberId(memberEmail, memberId);
	}

	@Transactional
	public Page<Board> selectAllBoard(int page) {
		return bdao.findAll(PageRequest.of(page, 10, Sort.by("isNotice").descending().and(Sort.by("boardNo").descending())));
	}
	@Transactional
	public Board selectOneBoard(int boardNo) {
		return bdao.findById(boardNo).orElse(null);
	}
	
	@Transactional
	public List<Board> searchBoardByBoardTitle(String boardTitle) {
		return bdao.findByBoardTitleContainingOrderByBoardNoDesc(boardTitle);
	}
	
	@Transactional
	public List<Board> searchBoardByBoardContent(String boardContent) {
		return bdao.findByBoardContentContainingOrderByBoardNoDesc(boardContent);
	}
	
	@Transactional
	public List<Board> searchBoardByMemberId(String memberId) {
		return bdao.findByMemberIdOrderByBoardNoDesc(memberId);
	}

	@Transactional
	public void insertOneBoard(Board board) {
		bdao.save(board);
	}

	@Transactional
	public void updateOneBoard(Board board) {
		bdao.save(board);
	}

	@Transactional
	public void deleteOneBoard(int boardNo) {
		bdao.deleteById(boardNo);
	}

	@Transactional
	public List<Comment> selectAllComment() {
		return cdao.findAll();
	}

	@Transactional
	public List<Comment> selectAllCommentByBoardNo(int boardNo) {
		return cdao.findAllByBoardNo(boardNo);
	}

	@Transactional
	public Comment selectOneComment(int commentNo) {
		return cdao.findById(commentNo).orElse(null);
	}

	@Transactional
	public void insertOneComment(Comment comment) {
		cdao.save(comment);
	}

	@Transactional
	public void updateOneComment(Comment comment) {
		cdao.save(comment);
	}

	@Transactional
	public void deleteOneComment(int commentNo) {
		cdao.deleteById(commentNo);
	}

	@Transactional
	public void addHit(int board_no) {
		Board board = (Board) bdao.findById(board_no).orElse(null);
		board.setBoardHit(board.getBoardHit() + 1);
		bdao.save(board);
	}

	@Transactional
	public void addComment(Comment comment) {
		cdao.save(comment);
	}

	@Transactional
	public void updateComment(Comment comment) {
		cdao.save(comment);
	}

	@Transactional
	public void deleteComment(int comment_no) {
		cdao.deleteById(comment_no);
	}

	@Transactional
	public Page<StockInfo> selectAllStockInfo(int page) {
		return sidao.findAll(PageRequest.of(page, 10, Sort.by("stockCode").ascending()));
	}

	@Transactional
	public List<StockInfo> selectAllStockInfoByStockCode(String stockCode) {
		return sidao.findByStockCodeLike("%" + stockCode + "%");
	}

	@Transactional
	public List<StockInfo> selectAllStockInfoByStockName(String stockName) {
		return sidao.findByStockNameLike("%" + stockName + "%");
	}

	@Transactional
	public List<Share> selectAllShareByStockCode(String stockCode) {
		return sdao.findByStockCode(stockCode);
	}

	@Transactional
	public List<HRR> selectAllHRRByStockCode(String stockCode) {
		return hdao.findByStockCode(stockCode);
	}

	@Transactional
	public StockInfo selectOneStockInfoByStockCode(String stockCode) {
		return sidao.findById(stockCode).get();
	}

	@Transactional
	public HRR selectOneHRRByStockCode(String stockCode) {
		return hdao.findByStockCodeAndBsnsYearAndReprtCode(stockCode, "2021", "11011");
	}
	
	@Transactional
	public Dividend selectOneDividendByStockCode(String StockCode) {
		return ddao.findById(StockCode).get();
	}
	
	@Transactional
	public void sendVerificationEmail(String id, String email) {
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
				helper.setFrom("officialpsythinktank@gmail.com");
				helper.setTo(email);
				helper.setSubject("PSYThinktank 이메일 인증 메일입니다.");
				helper.setText("", true);		
			}
		};
		mailSender.send(preparator);
	}
	
	@Transactional
	public void sendTempPwEmail(String id, String email) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i = 0; i < 11; i++) {
			sb.append((char) (random.nextInt(57) + 'A'));
		}
		String randomizedStr = sb.toString();
		Member member = mdao.findByMemberEmail(email);
		member.setMemberPw(BCrypt.hashpw(randomizedStr, BCrypt.gensalt()));
		member.setLoginTryCount(0);
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
				helper.setFrom("officialpsythinktank@gmail.com");
				helper.setTo(email);
				helper.setSubject("임시 비밀번호를 보내드립니다.");
				helper.setText("임시비밀번호는 "+ randomizedStr +"입니다.", true);
			}
		};
		mailSender.send(preparator);
	}
	
	@Transactional
	public Page<Circular> selectAllCircular(int page) {
		return crdao.findAll(PageRequest.of(page, 10, Sort.by("circularId").ascending()));
	}
	
	@Transactional
	public Circular selectOneCircular(int circularId) {
		return crdao.findById(circularId).get();
	}
	
	@Transactional
	public void insertOneCircular(Circular circular, HttpServletRequest request, MultipartFile file) {
		String realPath = request.getServletContext().getRealPath("/uploadfile");
		String filePath = realPath + "/" + circular.getFileName();
		System.out.println("경로: " + filePath);
		try {
			file.transferTo(new File(filePath));
			crdao.save(circular);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void deleteOneCircular(int circularId, HttpServletRequest request) {
		String realPath = request.getServletContext().getRealPath("/uploadfile");
		Circular circular = selectOneCircular(circularId);
		String filePath = realPath + "/" + circular.getFileName();
		try {
			new File(filePath).delete();
			crdao.deleteById(circularId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Resource downloadCircular(int circularId, HttpServletRequest request) {
		try {
			String realPath = request.getServletContext().getRealPath("/uploadfile");
			Circular circular = selectOneCircular(circularId);
			Path filePath = Paths.get(realPath + "/" + circular.getFileName());
			Resource resource = new UrlResource(filePath.toUri());
			System.out.println("uri: "+ filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
