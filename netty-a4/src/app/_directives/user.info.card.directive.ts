import { Directive, ElementRef, Renderer2, Input, OnInit } from '@angular/core';

// <span [appUserInfoCard]="'hotpink'"
//   [appShadowX]="'12px'"
//   [appShadowY]="'6px'"
//   [appShadowBlur]="'30px'">Alligator</span>

@Directive({ selector: '[appUserInfoCard]' })
export class UserInfoCardDirective implements OnInit {


  @Input() appShadow: string;
  @Input() appShadowX: string;
  @Input() appShadowY: string;
  @Input() appShadowBlur: string;

  constructor(private elem: ElementRef, private renderer: Renderer2) {
    renderer.setStyle(elem.nativeElement, 'box-shadow', '2px 2px 12px #58A362');
  }

  ngOnInit() {
    const shadowStr = `${ this.appShadowX } ${ this.appShadowY } ${ this.appShadowBlur } ${ this.appShadow }`;
    this.renderer.setStyle(this.elem.nativeElement, 'box-shadow', shadowStr);

  }
}
