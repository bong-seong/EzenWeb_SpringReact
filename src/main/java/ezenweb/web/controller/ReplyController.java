package ezenweb.web.controller;

import ezenweb.web.domain.board.ReplyDto;
import ezenweb.web.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @PostMapping("")
    public byte addReply(@RequestBody ReplyDto dto ){
        return replyService.addReply(dto);
    }

    @GetMapping("")
    public List<ReplyDto> getReply( @RequestParam int bno ){
        return replyService.getReply(bno);
    }

    @PutMapping("")
    public boolean updateReply( @RequestParam int rno ) {
        return false;
    }

    @DeleteMapping("")
    public byte deleteReply( ReplyDto replyDto ){
        log.info("ReplyDto : " + replyDto );
        return replyService.deleteReply(replyDto);
    }

}
