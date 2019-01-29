package edu.buffalo.cse.jive.finiteStateMachine.models;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class DisplayThread extends Thread {

	private GridData browserLData;
	private Text hcanvasText;
	private Text vcanvasText;
	private Browser browser;
	private String svg;
	private Composite imageComposite;
	private ScrolledComposite rootScrollComposite;
	private Control mainComposite;

	public DisplayThread(GridData browserLData, Text hcanvasText, Text vcanvasText, Browser browser, String svg,
			Composite imageComposite, ScrolledComposite rootScrollComposite, Control mainComposite) {
		super();
		this.browserLData = browserLData;
		this.hcanvasText = hcanvasText;
		this.vcanvasText = vcanvasText;
		this.browser = browser;
		this.svg = svg;
		this.imageComposite = imageComposite;
		this.rootScrollComposite = rootScrollComposite;
		this.mainComposite = mainComposite;
	}

	@Override
	public void run() {
		browserLData.widthHint = Integer.parseInt(hcanvasText.getText());
		browserLData.heightHint = Integer.parseInt(vcanvasText.getText());
		browser.setLayoutData(browserLData);
		browser.setText(svg);
		imageComposite.pack();
		rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

}