package me.thesis.master.common.service;

import lombok.extern.slf4j.Slf4j;
import me.thesis.master.common.models.BaseInView;
import me.thesis.master.common.models.BaseOrmBean;
import me.thesis.master.common.models.BaseOutView;
import me.thesis.master.common.repositories.BaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public abstract class BaseService<Orm extends BaseOrmBean,
        InView extends BaseInView,
        OutView extends BaseOutView> {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private final Class<Orm> ormClass;
    private final Class<InView> inClass;
    private final Class<OutView> outClass;

    protected abstract BaseRepository<Orm> getRepository();

    @Autowired(required = false)
    public BaseService(Class<Orm> ormBean, Class<InView> in, Class<OutView> out) {
        ormClass = ormBean;
        inClass = in;
        outClass = out;
    }
    public OutView getOne(UUID id) {
        log.info("Staring getOne for id {}", id);
        OutView outView = null;
        try {
            Optional<Orm> byId = this.getRepository().findById(id);
            if (byId.isEmpty()) {
                throw new NoSuchElementException("No such object!");
            }

            log.debug("Returning outView...");
            outView = mapToOutView(byId.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            log.info("Finished getOne for id {}. Returning {}", id, outView);
        }
        return outView;
    }

    public OutView postOne(InView inView) {
        log.info("Staring postOne for {}", inView);
        OutView outView = null;
        try {
            log.debug("Validating before post!");
            this.validateBeforePost(inView);

            log.debug("Mapping to orm...");
            Orm bean = mapToOrmBean(inView);

            log.debug("Saving...");
            Orm save = this.getRepository().save(bean);

            log.debug("Validating after post!");
            this.validateAfterPost(save);

            log.debug("Returning outView...");
            outView = mapToOutView(save);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            log.info("Finished postOne for {}. Returning {}", inView, outView);
        }
        return outView;
    }

    public OutView putOne(InView inView, UUID id) {
        log.info("Staring putOne for id {} and object {}", id, inView);
        OutView outView = null;
        try {
            log.debug("Validating before put!");
            this.validateBeforePut(inView);

            this.getRepository().deleteById(id);

            log.debug("Mapping to orm...");
            Orm bean = mapToOrmBean(inView);
            bean.setId(id);

            log.debug("Saving...");
            Orm save = this.getRepository().save(bean);

            log.debug("Validating after put!");
            this.validateAfterPut(save);

            log.debug("Returning outView...");
            outView = mapToOutView(save);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            log.info("Finished putOne for {} with id {}. Returning {}", inView, id, outView);
        }
        return outView;
    }

    public OutView deleteOne(UUID id) {
        log.info("Staring deleteOne for id {}", id);
        OutView outView = null;
        try {
            Optional<Orm> byId = this.getRepository().findById(id);
            if (byId.isEmpty()) {
                throw new NoSuchElementException("No such object!");
            }

            this.getRepository().deleteById(id);

            log.debug("Returning outView...");
            outView = mapToOutView(byId.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            log.info("Finished deleteOne for id {}. Returning {}", id, outView);
        }
        return outView;
    }

    protected void validateBeforePost(InView orm) {
    }

    protected void validateAfterPost(Orm orm) {
    }

    protected void validateBeforePut(InView orm) {
    }

    protected void validateAfterPut(Orm orm) {
    }

    private OutView mapToOutView(Orm bean) {
        return MODEL_MAPPER.map(bean, outClass);
    }

    private Orm mapToOrmBean(InView inView) {
        Orm map = MODEL_MAPPER.map(inView, ormClass);
        map.setId(UUID.randomUUID());

        return map;
    }
}
