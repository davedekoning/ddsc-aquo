/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nelen_schuurmans.aquo;

import java.util.Collection;

/**
 *
 * @author carsten.byrman@nelen-schuurmans.nl
 */
public interface GenericDAO<T> {

    public void createOrUpdate(Collection<T> entities);

    public void createOrUpdate(T entity);

    public T findByCode(String code);

    public T mostRecent();
}
