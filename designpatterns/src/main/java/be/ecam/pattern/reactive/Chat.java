package be.ecam.pattern.reactive;

import be.ecam.pattern.behavioral.observer.Message;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Chat {
    public static void main(String[] args) throws InterruptedException {

        // chatters
        Observable<Message> bob = Observable
                .just(new Message("bob", "Hello i am bob"))
                .observeOn(Schedulers.io());
        Observable<Message> alice = Observable
                .just(new Message("alice", "Hello i am alice"))
                .observeOn(Schedulers.io());
        Observable<Message> spammer = Observable
                .interval(5, TimeUnit.SECONDS)
                .observeOn(Schedulers.computation())
                .map(i -> new Message("spammer", "Buy my crap"))
                .repeat();

        // chat
        Observable.merge(alice, bob, spammer)
                .map(message -> {
                    if (message.getMessage().contains("crap")) {
                        String msg = message.getMessage().replace("crap", "stuff");
                        return new Message(message.getAuthor(), msg);
                    }
                    return message;
                }) // filtering insanity
                .subscribeOn(Schedulers.single())
                .subscribe(System.out::println);


        // Just to avoid main thread ot end.
        Thread.sleep(30_000);
    }
}
