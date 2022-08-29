package com.sun.service;

import com.sun.entity.Book;
import com.sun.entity.History;
import com.sun.entity.HistoryDetail;
import com.sun.entity.User;
import com.sun.exception.BookException;
import com.sun.exception.HistoryException;
import com.sun.exception.RequestException;
import com.sun.exception.UserException;
import com.sun.repository.BookRepository;
import com.sun.repository.HistoryDetailRepository;
import com.sun.repository.HistoryRepository;
import com.sun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RequestService {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private HistoryDetailRepository historyDetailRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    Logger logger = Logger.getLogger(HistoryService.class.getName());
    private boolean checkBorrow(int userId,int bookId){
        try{
        List<History> historys = historyRepository.findByUserId(userId);
        for(History history:historys){
        List<HistoryDetail> historyDetails = historyDetailRepository.getByHistoryId(history.getId());
        if(!historyDetails.isEmpty()){
            for(HistoryDetail historyDetail:historyDetails){
                if(historyDetail.getBookId().getId() == bookId){
                    return true;
                }
            }
        }}}catch (Exception ex){
            logger.warning(ex.getMessage());
        }
        return false;
    }
    @Transactional
    public String request(int userId, List<Book> books){
        System.out.println(userId);
        String msg = "Send Success";
        boolean check = false;
        for(Book book:books) {
            boolean check_borrow = checkBorrow(userId, book.getId());
            if(check_borrow){
                check =true;
                return "Have any book borrowed";
            }
        }
        if(!check){
            User user = userRepository.findById(userId).orElseThrow(()->new UserException(UserException.USER_NOT_FOUND));
            for(Book b:books) {
                Book book = bookRepository.findById(b.getId()).orElseThrow(() -> new BookException(BookException.BOOK_NOT_FOUND)
                );
            }
            History historyNew = new History(LocalDateTime.now(), History.EStatus.REQUEST,user);
            try {
                History history = historyRepository.save(historyNew);
                for(Book b:books) {
                    historyDetailRepository.save(new HistoryDetail(history, b));
                }
            }catch (Exception ex){
                msg = RequestException.CREATE_REQUEST_ERROR.getMessage();
                throw new RequestException(RequestException.CREATE_REQUEST_ERROR);
            }
        }else{
            msg = RequestException.DENIED_CREATE_REQUEST.getMessage();
            throw new RequestException(RequestException.DENIED_CREATE_REQUEST);
        }
        return msg;
    }
    @Transactional
    public String deleteRequest(int historyId){
        String msg = "Delete False";
        System.out.println(historyId);
        History history = historyRepository.findById(historyId).orElseThrow(()-> new HistoryException(HistoryException.HISTORY_NOT_FOUND));
        if(history.getStatus()== History.EStatus.REQUEST){
        try{
            historyDetailRepository.deleteByHistoryId(historyId);
            historyRepository.deleteById(historyId);
            msg = "Delete sussess";
        }catch (Exception ex){
            logger.warning(ex.getMessage());
        }}
        return msg;
    }
    @Transactional
    public String acceptRequest(int historyId){
        String msg = "Success";
        System.out.println(historyId);
        History history = historyRepository.findById(historyId).orElseThrow(()->new HistoryException(HistoryException.HISTORY_NOT_FOUND));
        if(history.getStatus()==History.EStatus.REQUEST){
        try{
            System.out.println("update");
            historyRepository.acceptRequest(historyId,LocalDateTime.now());
            System.out.println("hi");
        }catch (Exception ex){
            msg = "Accept false";
            logger.warning(ex.getMessage());
        }}else{
            msg = "Request Accepted";
        }
        return msg;
    }

}
