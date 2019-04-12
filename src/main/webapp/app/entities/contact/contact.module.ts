import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContactAppSharedModule } from 'app/shared';
import {
    ContactComponent,
    ContactDetailComponent,
    ContactUpdateComponent,
    ContactDeletePopupComponent,
    ContactDeleteDialogComponent,
    contactRoute,
    contactPopupRoute
} from './';
import { SendMessageDialogComponent } from './send-message-dialog.component';

const ENTITY_STATES = [...contactRoute, ...contactPopupRoute];

@NgModule({
    imports: [ContactAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactComponent,
        ContactDetailComponent,
        ContactUpdateComponent,
        ContactDeleteDialogComponent,
        ContactDeletePopupComponent,
        SendMessageDialogComponent
    ],
    entryComponents: [ContactComponent, ContactUpdateComponent, ContactDeleteDialogComponent, ContactDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContactAppContactModule {}
