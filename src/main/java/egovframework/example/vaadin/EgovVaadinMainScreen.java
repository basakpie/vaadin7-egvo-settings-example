package egovframework.example.vaadin;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.ListOrderedMap;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@UIScope
@SpringComponent
@SuppressWarnings({"serial", "unchecked"})
public class EgovVaadinMainScreen extends VerticalLayout {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@PostConstruct
	public void init() {
		setSizeFull();
		setMargin(true);
        setSpacing(true);
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(new ClickListener() {
            @Override   
            public void buttonClick(ClickEvent event) {
                addComponent(new Label("Thanks " + name.getValue() + ", it works!"));
            }
        });
        
        Table table = crateTable();
        
        addComponents(name, button, table);
        setExpandRatio(table, 1f);
	}
	
	private Table crateTable() {
    	
    	SampleDefaultVO searchVO = new SampleDefaultVO();    	
    	IndexedContainer container = new IndexedContainer();  
    	
    	try {    		
    		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    		searchVO.setPageSize(propertiesService.getInt("pageSize"));

    		/** pageing setting */
    		PaginationInfo paginationInfo = new PaginationInfo();
    		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
    		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
    		paginationInfo.setPageSize(searchVO.getPageSize());

    		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
    		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
    		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
    		    		
        	List<EgovMap> sampleList = (List<EgovMap>) sampleService.selectSampleList(searchVO);     
        	
        	if(sampleList.size()>0) {
    			container.removeAllItems();
    		}
        	
    		for(int i=0; i< sampleList.size(); i++) {    			
    			EgovMap map = sampleList.get(i);
    			if(i==0) {
    				for(Object key : map.keyList()) {
    					container.addContainerProperty(String.valueOf(key), String.class, null);
    				}
    			}		
    			Object itemId = container.addItem();
    			MapIterator it = ((ListOrderedMap)map).mapIterator();
    			while(it.hasNext()) {    				    				
    				String key = String.valueOf(it.next());
    				String value = String.valueOf(it.getValue());
    				container.getContainerProperty(itemId, key).setValue(value);    				
    			}
    		}  
    		
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    	
    	Table table = new Table();
    	table.setSizeFull();
    	table.setContainerDataSource(container);
    	table.setStyleName(ValoTheme.TABLE_SMALL);    	    	
    	table.setVisibleColumns("id", "name", "useYn", "description", "regUser");
    	table.setColumnHeaders("카테고리ID", "카테고리명", "사용여부", "설명", "등록자");
    	
        return table;
    }
}
