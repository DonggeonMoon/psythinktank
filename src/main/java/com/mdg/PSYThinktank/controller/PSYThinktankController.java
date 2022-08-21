package com.mdg.PSYThinktank.controller;

import com.mdg.PSYThinktank.model.*;
import com.mdg.PSYThinktank.service.PSYThinktankService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PSYThinktankController {
    private final PSYThinktankService psyThinktankService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login2(@RequestBody HashMap<String, String> map, HttpSession session) {
        return psyThinktankService.login(map.get("memberId"), map.get("memberPw"), session);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("member");
        return "redirect:/";
    }

    @GetMapping("/member")
    public String insertMemberInfo() {
        return "join";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public Map<Object, Object> checkId(@RequestBody String memberId) {
        HashMap<Object, Object> map = new HashMap<>();
        if (!psyThinktankService.checkId(memberId)) {
            map.put("isUnique", true);
        } else {
            map.put("isUnique", false);
        }
        return map;
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<Object, Object> checkEmail(@RequestBody String memberEmail) {
        HashMap<Object, Object> map = new HashMap<>();
        if (!psyThinktankService.checkEmail(memberEmail)) {
            map.put("isUnique2", true);
        } else {
            map.put("isUnique2", false);
        }
        return map;
    }

    @PostMapping("/member")
    public String insertMemberInfo2(HttpSession session, Member member) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            psyThinktankService.join(member);
        } else {
            session.removeAttribute(null);
        }
        return "redirect:/login";
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPw() {
        return "findIdAndPw";
    }

    @PostMapping("/findId")
    @ResponseBody
    public Map<String, Object> findId(@RequestBody HashMap<String, String> member) {
        Map<String, Object> map = new HashMap<>();
        Member result = psyThinktankService.selectOneMemberByEmail(member.get("memberEmail"));
        if (result != null) {
            map.put("exists", true);
            map.put("id", result.getMemberId());
        } else {
            map.put("exists", false);
            map.put("id", null);
        }
        return map;
    }

    @PostMapping("/findPw")
    @ResponseBody
    public Map<String, Object> findPw(@RequestBody HashMap<String, String> member) {
        Map<String, Object> map = new HashMap<>();
        Member result = psyThinktankService.selectOneMemberByEmailAndId(member.get("memberEmail"), member.get("memberId"));
        if (result != null) {
            map.put("exists", true);
            psyThinktankService.sendTempPwEmail(result.getMemberEmail());
        } else {
            map.put("exists", false);

        }
        return map;
    }

    @GetMapping("/editMemberInfo")
    public String editMemberInfo(HttpSession session, Model model) {
        if (session.getAttribute("member") == null) {
            return "redirect:/login";
        }
        model.addAttribute("memberInfo", psyThinktankService.getMemberInfo(session));
        return "editMemberInfo";
    }

    @PutMapping("/member")
    public String editMemberInfo2(HttpSession session, Member member) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(member.getMemberId())) {
                psyThinktankService.editMemberInfo(member);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("/member")
    public String deleteMemberInfo(HttpSession session, String memberId) throws IOException {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                psyThinktankService.deleteMemberInfo(memberId);
                session.removeAttribute("member");
                return "redirect:/goodBye";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/goodBye")
    public String goodBye() {
        return "goodBye";
    }

    @GetMapping("/managerPage")
    public String managerPage(HttpSession session, @RequestParam(defaultValue = "1") int page, Model model) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getUserLevel() == 3) {
                Page<Member> memberPage = psyThinktankService.selectAllMember(page - 1);
                model.addAttribute("memberList", memberPage);
                model.addAttribute("currentBlock", memberPage.getNumber() / memberPage.getSize());
                return "managerPage";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/changeUserLevel")
    public String changeUserLevel(HttpSession session, Member member) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getUserLevel() == 3) {
                psyThinktankService.changeUserLevel(member);
                return "redirect:/managerPage";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/boardList")
    public String boardList(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<Board> boardPage = psyThinktankService.selectAllBoard(page - 1);
        model.addAttribute("boardList", boardPage);
        model.addAttribute("currentBlock", boardPage.getNumber() / boardPage.getSize());
        return "boardList";
    }

    @PostMapping("/searchByBoardTitle")
    @ResponseBody
    public Map<String, Object> searchBoardByBoardTitle(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", psyThinktankService.searchBoardByBoardTitle(searchText));
        return map;
    }

    @PostMapping("/searchByBoardContent")
    @ResponseBody
    public Map<String, Object> searchBoardByBoardContent(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", psyThinktankService.searchBoardByBoardContent(searchText));
        return map;
    }

    @PostMapping("/searchByMemberId")
    @ResponseBody
    public Map<String, Object> searchBoardByMemberId(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", psyThinktankService.searchBoardByMemberId(searchText));
        return map;
    }

    @GetMapping("/board")
    public String viewBoard(int boardNo, Model model) {
        psyThinktankService.addHit(boardNo);
        model.addAttribute("board", psyThinktankService.selectOneBoard(boardNo));
        model.addAttribute("commentList", psyThinktankService.selectAllCommentByBoardNo(boardNo));
        return "viewBoard";
    }

    @GetMapping("/insertBoard")
    public String insertBoard(HttpSession session) {
        if (session.getAttribute("member") == null) {
            return "redirect:/login";
        }
        return "insertBoard";
    }

    @PostMapping("/board")
    public String insertBoard2(HttpSession session, Board board) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                psyThinktankService.insertOneBoard(board);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/updateBoard")
    public String updateBoard(HttpSession session, int boardNo, Model model) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            Board board = psyThinktankService.selectOneBoard(boardNo);
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                model.addAttribute("board", psyThinktankService.selectOneBoard(boardNo));
                return "updateBoard";
            } else {
                return "redirect:/login";
            }
        }

    }

    @PutMapping("/board")
    public String updateBoard2(HttpSession session, Board board) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(board.getMemberId())) {
                psyThinktankService.updateOneBoard(board);
                return "redirect:/board?boardNo=" + board.getBoardNo();
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("/board")
    public String deleteBoard(HttpSession session, int boardNo, String memberId) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                psyThinktankService.deleteOneBoard(boardNo);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("comment")
    public String addComment(HttpSession session, Comment comment) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                psyThinktankService.addComment(comment);
                return "redirect:/board?boardNo=" + comment.getBoardNo();
            } else {
                return "redirect:/login";
            }
        }
    }

    @PutMapping("comment")
    public String updateComment(HttpSession session, Comment comment) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(comment.getMemberId())) {
                psyThinktankService.updateComment(comment);
                return "redirect:/board?boardNo=" + comment.getBoardNo();
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("comment")
    public String deleteComment(HttpSession session, int commentNo, int boardNo) {
        Member sessionInfo = (Member) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(psyThinktankService.selectOneComment(commentNo).getMemberId())) {
                psyThinktankService.deleteComment(commentNo);
                return "redirect:/board?boardNo=" + boardNo;
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/stockList")
    public String stockList(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<StockInfo> stockPage = psyThinktankService.selectAllStockInfo(page - 1);
        model.addAttribute("stockList", stockPage);
        model.addAttribute("currentBlock", stockPage.getNumber() / stockPage.getSize());
        return "stockList";
    }

    @PostMapping("/searchByStockCode")
    @ResponseBody
    public Map<String, Object> searchByStockCode(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", psyThinktankService.selectAllStockInfoByStockCode(searchText));
        return map;
    }

    @PostMapping("/searchByStockName")
    @ResponseBody
    public Map<String, List<StockInfo>> searchByStockName(@RequestBody String searchText) {
        Map<String, List<StockInfo>> map = new HashMap<>();
        map.put("result", psyThinktankService.selectAllStockInfoByStockName(searchText));
        return map;
    }

    @GetMapping("/viewStock")
    public String viewStock(String stockCode, Model model) {
        model.addAttribute("stock", psyThinktankService.selectOneStockInfoByStockCode(stockCode));
        model.addAttribute("share", psyThinktankService.selectAllShareByStockCode(stockCode));
        model.addAttribute("hrr", psyThinktankService.selectOneHRRByStockCode(stockCode));
        model.addAttribute("dividend", psyThinktankService.selectOneDividendByStockCode(stockCode));
        return "viewStock";
    }

    @GetMapping("/circularList")
    public String circularList(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<Circular> circularPage = psyThinktankService.selectAllCircular(page - 1);
        model.addAttribute("circularList", circularPage);
        model.addAttribute("currentBlock", circularPage.getNumber() / circularPage.getSize());
        return "circularList";
    }

    @GetMapping("/circular")
    @ResponseBody
    public ResponseEntity<Resource> viewCircular(@RequestParam(defaultValue = "1") int circularId, Model model, HttpServletRequest request) {
        Resource file = psyThinktankService.downloadCircular(circularId, request);
        ResponseEntity<Resource> response = ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf").body(file);
        model.addAttribute("circular", psyThinktankService.selectOneCircular(circularId));
        return response;
    }

    @GetMapping("/insertCircular")
    public String insertCircular() {
        return "insertCircular";
    }

    @PostMapping("/circular")
    public String insertCircular2(Circular circular, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID() + "." + extension;
            circular.setFileName(fileName);
            psyThinktankService.insertOneCircular(circular, request, file);
        }
        return "redirect:/circularList";
    }

    @DeleteMapping("/circular")
    public String deleteCircular(int circularId, HttpServletRequest request) {
        psyThinktankService.deleteOneCircular(circularId, request);
        return "redirect:/circularList";
    }
}
