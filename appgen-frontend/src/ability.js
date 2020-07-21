import { AbilityBuilder, Ability } from '@casl/ability';
import store from './store.js';

export default function() {
    const { rules, can, cannot } = AbilityBuilder.extract();
    let user = store.state.user;
    switch (user.role){
        case 'SUPER_ADMIN':
            can('view','lessees');
            can('view','serviceProviders');
            can('view','users');
            can('edit','users');
            can('view','reports');
            break;
        case 'LEASE_OPERATOR_ADMIN':
            can('view','lessees');
            can('view','serviceProviders');
            can('view','users');
            can('edit','users');
            can('view','reports');
            break;
        case 'LEASE_OPERATOR_USER':
            can('view','lessees');
            can('view','serviceProviders');
            can('view','users');
            can('edit','users');
            can('view','reports')
            break;
        case 'SERVICE_COMPANY_ADMIN':
            can('view','lessees');
            can('view','serviceProviders');
            can('view','users');
            can('edit','users');
            can('view','reports');
            break;
        case 'SERVICE_COMPANY_USER':
            can('view','lessees');
            can('view','serviceProviders');
            can('view','users');
            can('edit','users');
            can('view','reports');
            break;
        default:
            can('view','lessees');
            can('view','serviceProviders');
            can('view','users');
            can('edit','users');
            can('view','reports');
    }

    return new Ability(rules);
}
