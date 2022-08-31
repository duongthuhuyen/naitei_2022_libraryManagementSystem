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
import java.util.ArrayList;
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

    private boolean checkBorrow(int userId, int bookId) {
        try {
            List<History> historys = historyRepository.findByUserId(userId);
            for (History history : historys) {
                List<HistoryDetail> historyDetails = historyDetailRepository.getByHistoryId(history.getId());
                if (!historyDetails.isEmpty()) {
                    for (HistoryDetail historyDetail : historyDetails) {
                        if (historyDetail.getBookId().getId() == bookId) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
        return false;
    }

    @Transactional
    public String request(int userId, List<Integer> books) {
        String msg = "Send Success";
        for (Integer book : books) {
            boolean check_borrow = checkBorrow(userId, book);
            if (check_borrow) {
                return "Have any book borrowed";
            }
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));
        List<Book> books1 = new ArrayList<>();
        for (Integer b : books) {
            Book book = bookRepository.findById(b).orElseThrow(() -> new BookException(BookException.BOOK_NOT_FOUND));
            books1.add(book);
        }
        History historyNew = new History(LocalDateTime.now(), History.EStatus.REQUEST, user);
        try {
            History history = historyRepository.save(historyNew);
            for (Book b : books1) {
                historyDetailRepository.save(new HistoryDetail(history, b));
            }
        } catch (Exception ex) {
            msg = RequestException.CREATE_REQUEST_ERROR.getMessage();
            throw new RequestException(RequestException.CREATE_REQUEST_ERROR);
        }
        return msg;
    }

    @Transactional
    public String deleteRequest(int historyId) {
        String msg = "Delete False";
        History history = historyRepository.findById(historyId).orElseThrow(() -> new HistoryException(HistoryException.HISTORY_NOT_FOUND));
        if (history.getStatus() == History.EStatus.REQUEST) {
            try {
                historyDetailRepository.deleteByHistoryId(historyId);
                historyRepository.deleteById(historyId);
                msg = "Delete sussess";
            } catch (Exception ex) {
                logger.warning(ex.getMessage());
            }
        }
        return msg;
    }

    @Transactional
    public String acceptRequest(int historyId) {
        String msg = "Success";
        History history = historyRepository.findById(historyId).orElseThrow(() -> new HistoryException(HistoryException.HISTORY_NOT_FOUND));
        if (history.getStatus() == History.EStatus.REQUEST) {
            try {
                historyRepository.acceptRequest(historyId, LocalDateTime.now());
            } catch (Exception ex) {
                msg = "Accept false";
                logger.warning(ex.getMessage());
            }
        } else {
            msg = "Request Accepted";
        }
        return msg;
    }

}
