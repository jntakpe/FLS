package fr.sg.fls.domain;

import org.springframework.core.io.ByteArrayResource;

/**
 * @author jntakpe
 */
public class SctResource extends ByteArrayResource {

    public SctResource(String msg) {
        super(msg.getBytes());
    }
}
